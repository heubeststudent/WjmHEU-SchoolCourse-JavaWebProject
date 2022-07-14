package com.zr.controller.lease;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zr.service.lease.OrderService;
import com.zr.util.AjaxResult;
import com.zr.vo.car.Work;
import com.zr.vo.lease.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lease/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 查询订单
     * @param order
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestBody Order order){
        //查询订单列表
        List<Order> orderList = orderService.list(order);
        //封装到分页对象中
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        return JSON.toJSONString(AjaxResult.success("查询成功", pageInfo));
    }

    @RequestMapping("/detail")
    public String detail(@RequestBody Work work){
        Order order = orderService.detail(work.getOrderId());
        return JSON.toJSONString(AjaxResult.success("成功",order));
    }

    @RequestMapping("/detail2")
    public String detail2(@RequestBody Work work){
        Order order = orderService.detail2(work.getOrderId());
        return JSON.toJSONString(AjaxResult.success("成功",order));
    }
}
