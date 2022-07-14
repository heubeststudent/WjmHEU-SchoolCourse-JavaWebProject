package com.zr.controller.car;


import com.alibaba.fastjson.JSON;
import com.zr.service.car.CarService;
import com.zr.util.AjaxResult;
import com.zr.vo.car.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/car/car")
public class CarController {

    @Autowired
    private CarService carService;


    /**
     * 获取所有车列表
     * @return
     */
    @RequestMapping("/selectList")
    public String selectList(){
        List<Car> carList = carService.selectList();
        return JSON.toJSONString(AjaxResult.success("查询成功",carList ));
    }



    @RequestMapping("/carList")
    public String list(@RequestBody Car car){
        List<Car> carList = carService.list(car);
        return  JSON.toJSONString(AjaxResult.success("查询成功",carList));
    }

    @RequestMapping("/add")
    public String add(@RequestBody Car car){
        carService.add(car);
        return JSON.toJSONString(AjaxResult.success("新增成功"));
    }

    @RequestMapping("/edit")
    public String edit(@RequestBody Car car){
        carService.edit(car);
        return JSON.toJSONString(AjaxResult.success("编辑成功"));
    }

    @RequestMapping("/del/{id}")
    public String del(@PathVariable("id") Long id){
        carService.del(id);
        return JSON.toJSONString(AjaxResult.success("删除成功"));
    }

    @RequestMapping("/uploadImg")
    public String uploadImg(@RequestBody Car car){
        carService.uploadImg(car.getImgUrl());
        //String base64 = carService.;
        return JSON.toJSONString(AjaxResult.success("上传成功"));
    }
}
