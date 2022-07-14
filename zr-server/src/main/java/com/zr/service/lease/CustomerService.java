package com.zr.service.lease;


import com.zr.vo.lease.Customer;
import com.zr.vo.lease.Order;

import java.util.List;

public interface CustomerService {
    List<Order> list(Customer customer);

    void add(Customer customer);

    void edit(Customer customer);

    void delAllByCustomerId(String customerId);
}
