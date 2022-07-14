package com.zr.controller.shop;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zr.mapper.shop.ShopMapper;
import com.zr.service.shop.ShopService;
import com.zr.util.AjaxResult;
import com.zr.util.RedisUtils;
import com.zr.vo.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 门店操作类
 */
@RestController
@RequestMapping("/shop/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @Resource
    private ShopMapper shopMapper;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 查询城市列表
     * @param shop
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestBody Shop shop) {
        //查询城市列表
        List<Shop> shopList = shopService.list(shop);
        //封装到分页对象中
        PageInfo<Shop> pageInfo = new PageInfo<>(shopList);
        return JSON.toJSONString(AjaxResult.success("查询成功", pageInfo));
    }

    /**
     *添加门店
     * @param shop
     * @return
     */
    @RequestMapping("/add")
    public String add(@RequestBody Shop shop){
        shopService.add(shop);
        return JSON.toJSONString(AjaxResult.success("新增成功"));
    }

    /**
     *编辑门店
     * @param shop
     * @return
     */
    @RequestMapping("/edit")
    public String edit(@RequestBody Shop shop){
        shopService.edit(shop);
        return JSON.toJSONString(AjaxResult.success("修改成功"));
    }

    /**
     *删除门店
     * @param id
     * @return
     */
    @RequestMapping("/del/{id}")
    public String edit(@PathVariable("id") Long id){
        shopService.del(id);
        return JSON.toJSONString(AjaxResult.success("删除成功"));
    }
}
