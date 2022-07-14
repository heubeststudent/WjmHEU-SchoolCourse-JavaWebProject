package com.zr.controller.shop;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zr.mapper.shop.CityMapper;
import com.zr.service.shop.CityService;
import com.zr.util.AjaxResult;
import com.zr.util.RedisUtils;
import com.zr.vo.shop.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 城市操作类
 */
@RestController
@RequestMapping("/shop/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @Resource
    private CityMapper cityMapper;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 查询城市列表
     * @param city
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestBody City city) {
        //查询城市列表
        List<City> cityList = cityService.list(city);
        //封装到分页对象中
        PageInfo<City> pageInfo = new PageInfo<>(cityList);
        return JSON.toJSONString(AjaxResult.success("查询成功", pageInfo));
    }

    /**
     *添加城市
     * @param city
     * @return
     */
    @RequestMapping("/add")
    public String add(@RequestBody City city){
        cityService.add(city);
        return JSON.toJSONString(AjaxResult.success("新增成功"));
    }

    /**
     *编辑城市
     * @param city
     * @return
     */
    @RequestMapping("/edit")
    public String edit(@RequestBody City city){
        cityService.edit(city);
        return JSON.toJSONString(AjaxResult.success("修改成功"));
    }

    /**
     *删除城市
     * @param id
     * @return
     */
    @RequestMapping("/del/{id}")
    public String edit(@PathVariable("id") Long id){
        cityService.del(id);
        return JSON.toJSONString(AjaxResult.success("删除成功"));
    }
    /**
     * 获取省份对应存在的城市列表
     * @return
     */
    @RequestMapping("/cityselectlist/{provinceId}")
    public String cityselectlist(@PathVariable("provinceId") Long provinceId){
        List<City> cityList = cityService.cityselectlist(provinceId);
        return JSON.toJSONString(AjaxResult.success("查询成功",cityList));
    }
}
