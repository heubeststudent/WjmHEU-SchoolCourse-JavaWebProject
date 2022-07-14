package com.zr.controller.shop;

import com.alibaba.fastjson.JSON;
import com.zr.service.shop.ProvinceService;
import com.zr.util.AjaxResult;
import com.zr.vo.shop.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop/province")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;
    /**
     * 获取所有省份列表
     * @return
     */
    @RequestMapping("/proselectlist")
    public String proselectlist(){
        List<Province> provinceList = provinceService.proselectlist();
        return JSON.toJSONString(AjaxResult.success("查询成功",provinceList));
    }

}
