package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TbBrandMapper brandMapper;

    @Override
    public List<TbBrand> findAll() {
        return brandMapper.selectByExample(null);
    }


    /**
     * 分页查询
     * @param pageNum 当前页面
     * @param pageSize 每页记录数
     * @return
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {



        PageHelper.startPage(pageNum, pageSize);

        Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);

        return new PageResult(page.getTotal(),page.getResult());

    }


    /**
     * 品牌新增
     * @param brand 品牌实体类
     */
    @Override
    public void add(TbBrand brand) {
        brandMapper.insert(brand);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public TbBrand findOne(Long id) {

        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 品牌更新
     * @param tbBrand
     */
    @Override
    public void update(TbBrand tbBrand) {
        brandMapper.updateByPrimaryKey(tbBrand);
    }

    @Override
    public void delete(Long [] ids) {
        for(Long id : ids){
            brandMapper.deleteByPrimaryKey(id);
        }

    }

    /**
     * 按条件查询咯
     * @param brand     查询条件
     * @param pageNum   当前页
     * @param pageSize  每页记录数
     * @return
     */
    @Override
    public PageResult findPage(TbBrand brand, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbBrandExample tbBrandExample = new TbBrandExample();

        TbBrandExample.Criteria criteria = tbBrandExample.createCriteria();

        if(brand!=null){
            if(brand.getName()!=null&&brand.getName().trim().length()>0){
                criteria.andNameLike("%"+brand.getName().trim()+"%");
            }
            if(brand.getFirstChar()!=null){
                criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");
            }
        }

        Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(tbBrandExample);

        return new PageResult(page.getTotal(),page.getResult());

    }

    @Override
    public List<Map> selectOptionList() {
        return brandMapper.selectOptionList();
    }


}
