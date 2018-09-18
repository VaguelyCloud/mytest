package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;
import java.util.Map;

/*
* 品牌接口
* @author Administrator
*
* */
public interface BrandService {


    /**
     * 查询全部~~
     *
     * @return 品牌列表
     */
    public List<TbBrand> findAll();

    /**
     * 品牌分页
     * @param pageNum 当前页面
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 增加
     * @param brand 品牌实体类
     */
    public void add(TbBrand brand);


    /**
     * 根据id查询实体
     * @param id
     * @return
     */
    public TbBrand findOne(Long id);

    /**
     * 修改
     * @param tbBrand
     */
    public void update(TbBrand tbBrand);

    public void delete(Long [] ids);


    /**
     * 按条件从查询
     * @param brand     查询条件
     * @param pageNum   当前页
     * @param pageSize  每页记录数
     * @return
     */
    public PageResult findPage(TbBrand brand,int pageNum, int pageSize);

    /**
     * 返回下拉列表
     * @return
     */
    public List<Map> selectOptionList();
}
