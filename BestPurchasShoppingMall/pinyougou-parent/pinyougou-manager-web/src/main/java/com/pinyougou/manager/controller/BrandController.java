package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/brand")
public class BrandController {


    @Reference
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){
        return brandService.findAll();
    }


    @RequestMapping("/findPage")
    public PageResult findPage(int page ,int size){
        PageResult pageResult = brandService.findPage(page, size);
        return pageResult;
    }

    /**
     * 品牌增加
     * @param brand
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TbBrand brand){

        try {
            brandService.add(brand);
            return new Result(true,"增加成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new Result(false,"增加失败");
        }
    }

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public TbBrand findOne(Long id){
        TbBrand brand = brandService.findOne(id);
        return brand;
    }


    /**
     * 更新品牌信息
     * @param brand
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbBrand brand){

        try {
            brandService.update(brand);
            return new Result(true,"更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"更新失败");
        }
    }


    /**
     * 根据id删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long [] ids){

        try {
            brandService.delete(ids);
            return new Result(true,"删除成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new Result(false,"删除失败");
        }

    }

    /**
     * 条件查询+分页
     * @param brand
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbBrand brand,int page,int size){
        return brandService.findPage(brand,page,size);
    }


    /**
     *
     * @return
     */
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }


}
