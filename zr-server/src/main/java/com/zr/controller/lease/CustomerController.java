package com.zr.controller.lease;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zr.service.lease.CustomerService;
import com.zr.service.lease.OrderService;
import com.zr.util.AjaxResult;
import com.zr.vo.lease.Customer;
import com.zr.vo.lease.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lease/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    /**
     * 查询用户
     * @param customer
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestBody Customer customer){
        //查询用户列表
        List<Order> orderList = customerService.list(customer);
        //封装到分页对象中
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        return JSON.toJSONString(AjaxResult.success("查询成功", pageInfo));
    }

    /**
     * 添加用户
     * @param customer
     * @return
     */
    @RequestMapping("/add")
    public String add(@RequestBody Customer customer){
        customerService.add(customer);
        return JSON.toJSONString(AjaxResult.success("新增成功"));
    }

    /**
     * 编辑用户
     * @param customer
     * @return
     */
    @RequestMapping("/edit")
    public String edit(@RequestBody Customer customer){
        customerService.edit(customer);
        return JSON.toJSONString(AjaxResult.success("编辑成功"));
    }

    /**
     * 删除用户根据用户id
     * @param customerId
     * @return
     */
    @RequestMapping("/del/{customerId}")
    public String del(@PathVariable("customerId")String customerId) {
        //删除该用户
        customerService.delAllByCustomerId(customerId);
        return JSON.toJSONString(AjaxResult.success("删除成功"));
    }

}
