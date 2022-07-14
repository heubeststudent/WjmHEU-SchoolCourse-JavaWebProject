package com.zr.controller.client;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zr.service.client.CustomerOrderService;
import com.zr.util.AjaxResult;
import com.zr.util.RedisUtils;
import com.zr.vo.lease.Customer;
import com.zr.vo.lease.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("client")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("listCustomerOrder")
    public String listCustomerOrder(HttpServletRequest request, @RequestBody Order order) {
        String token = request.getHeader("token");
        Customer loginCustomer = (Customer) redisUtils.get(token);
        System.out.println(loginCustomer.getName());
        //查询订单列表
        List<Order> customerOrderList = customerOrderService.listCustomerOrder(loginCustomer.getCustomerId(), order);
        //封装到分页对象中
        PageInfo<Order> pageInfo = new PageInfo<>(customerOrderList);
        return JSON.toJSONString(AjaxResult.success("查询成功", pageInfo));
    }

}
