package com.zr.service.client;

import com.github.pagehelper.PageHelper;
import com.zr.mapper.lease.CustomerMapper;
import com.zr.mapper.lease.OrderMapper;
import com.zr.vo.lease.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerOrderService {

    @Autowired
    private OrderMapper orderMapper;

    public List<Order> listCustomerOrder(String customerId, Order order) {
        PageHelper.startPage(order.getPageNum(), order.getPageSize());
        return orderMapper.selectCustomerOrder(customerId,order);
    }

}
