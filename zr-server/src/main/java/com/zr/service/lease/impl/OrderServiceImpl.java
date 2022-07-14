package com.zr.service.lease.impl;


import com.github.pagehelper.PageHelper;
import com.zr.mapper.lease.OrderMapper;
import com.zr.service.lease.OrderService;
import com.zr.vo.lease.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Resource
    private OrderMapper orderMapper;

    /**
     * 查询订单
     * @param order
     * @return
     */
    @Override
    public List<Order> list(Order order) {
        //分页插件使用，放在接口方法前，只生效一次
        PageHelper.startPage(order.getPageNum(), order.getPageSize());
        return orderMapper.list(order);
    }

    @Override
    public Order detail(Long orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public Order detail2(Long orderId) {
        return orderMapper.select2(orderId);
    }
}
