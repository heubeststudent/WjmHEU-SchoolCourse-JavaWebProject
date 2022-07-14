package com.zr.mapper.lease;

import com.zr.vo.lease.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> list(Order order);

    void delAllByCustomerId(String customerId);

    Order select2(Long orderId);

    Order selectByOrderId(Long id);

    List<Order> selectCustomerOrder(String customerId, Order order);
}