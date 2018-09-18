package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojogroup.Specification;
import com.pinyougou.sellergoods.service.SpecificationService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private TbSpecificationMapper specificationMapper;

    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbSpecification> findAll() {
        return specificationMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(Specification specification) {
        TbSpecification tbSpecification = specification.getSpecification();


        specificationMapper.insert(tbSpecification);//通过插入名称之后, 这个对象就会获取自动生成出来的id

        List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
        for (TbSpecificationOption tb : specificationOptionList) {
            tb.setSpecId(tbSpecification.getId());//然后此处获取上面生成出来的id
            specificationOptionMapper.insert(tb);//然后再执行循环插入
        }
    }


    /**
     * 修改
     */
    @Override
    public void update(Specification specification) {
        //先修改父名称
        specificationMapper.updateByPrimaryKey(specification.getSpecification());

        //再根据父id 创建详细表的条件,
        TbSpecificationOptionExample tbOptionExample = new TbSpecificationOptionExample();

        TbSpecificationOptionExample.Criteria criteria = tbOptionExample.createCriteria();

        criteria.andSpecIdEqualTo(specification.getSpecification().getId());

        //跟据条件,先删除,后增加,达到修改效果
        specificationOptionMapper.deleteByExample(tbOptionExample);

        List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();

        for (TbSpecificationOption tb : specificationOptionList) {
            tb.setSpecId(specification.getSpecification().getId());//然后此处获取上面生成出来的id
            specificationOptionMapper.insert(tb);//然后再执行循环插入
        }

    }

    /**
     * 根据ID获取 综合实体类
     *
     * @param id
     * @return
     */
    @Override
    public Specification findOne(Long id) {

        //先根据规格名称表id 获取规格名称表
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);

        //再查询明细表, 使用条件查询, 根据spec_id 查询出所有 与 当前规格相关的明细
        TbSpecificationOptionExample tbSpecificationOptionExample=new TbSpecificationOptionExample();

        TbSpecificationOptionExample.Criteria criteria=tbSpecificationOptionExample.createCriteria();

        criteria.andSpecIdEqualTo(tbSpecification.getId());

        List<TbSpecificationOption> tbSpecificationOptions = specificationOptionMapper.selectByExample(tbSpecificationOptionExample);

        //封装综合实体类
        Specification specification = new Specification();

        specification.setSpecification(tbSpecification);
        specification.setSpecificationOptionList(tbSpecificationOptions);

        return specification;


    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            specificationMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbSpecificationExample example = new TbSpecificationExample();
        Criteria criteria = example.createCriteria();

        if (specification != null) {
            if (specification.getSpecName() != null && specification.getSpecName().length() > 0) {
                criteria.andSpecNameLike("%" + specification.getSpecName() + "%");
            }

        }

        Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 模板 规格下拉列表
     * @return
     */
    @Override
    public List<Map> selectSpecList() {
        return specificationMapper.selectSpecList();
    }

}
