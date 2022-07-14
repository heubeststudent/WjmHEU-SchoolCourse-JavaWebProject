package com.zr.controller.car;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zr.service.car.WorkService;
import com.zr.util.AjaxResult;
import com.zr.vo.car.Work;
import com.zr.vo.sys.User;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * Create by zhangweilei
 * Date 2022/7/11 下午1:36
 * Description
 **/
@RestController
@RequestMapping("/car/work")
public class WorkController {

    @Resource
    private WorkService workService;

    @RequestMapping("/list")
    public String list(@RequestBody Work work){
        List<Work> list = workService.list(work);
        //封装到分页对象中
        PageInfo<Work> pageInfo = new PageInfo<>(list);
        return JSON.toJSONString(AjaxResult.success("查询成功", pageInfo));
    }

    @RequestMapping("/selectTotal")
    public String selectTotal(@RequestBody Work work){
        Work work1 = workService.selectTotal(work.getId());
        return JSON.toJSONString(AjaxResult.success("查询成功", work1));
    }


    @RequestMapping("/updateWork")
    public String updateWork(@RequestParam(value = "optId") String optId, @RequestParam(value = "price") String price, @RequestParam(value = "msg") String msg){
        Double p = Double.valueOf(price);
        int id = Integer.parseInt(optId);
        workService.update(id, p, msg);
        return JSON.toJSONString(AjaxResult.success("成功"));
    }

    @RequestMapping("/outCar")
    public String outCar(@RequestParam(value = "optId") String optId, @RequestParam(value = "carNo") String carNo){
        int id = Integer.parseInt(optId);
        workService.carOut(id, carNo);
        return JSON.toJSONString(AjaxResult.success("成功"));
    }

    @RequestMapping("/fit")
    public String outCar(@RequestParam(value = "id") String optId){
        long id = Integer.parseInt(optId);
        workService.fit(id);
        return JSON.toJSONString(AjaxResult.success("成功"));
    }

    @RequestMapping("/workList")
    public String worklist(@RequestBody Work work){
        List<Work> workList = workService.worklist(work);
        return  JSON.toJSONString(AjaxResult.success("查询成功",workList));
    }

    @RequestMapping("/selectwork")
    public String selectWork(@RequestBody Work work){
        List<Work> selectwork = workService.selectwork(work);
        return  JSON.toJSONString(AjaxResult.success("查询成功",selectwork));
    }


}
