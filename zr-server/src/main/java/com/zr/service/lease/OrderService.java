package com.zr.service.lease;

import com.zr.vo.lease.Order;

import java.util.List;

public interface OrderService {
    List<Order> list(Order order);

    Order detail(Long orderId);

    Order detail2(Long orderId);
}
