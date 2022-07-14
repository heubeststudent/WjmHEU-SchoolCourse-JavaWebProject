package com.zr.mapper.lease;

import com.zr.vo.car.Car;
import com.zr.vo.lease.Customer;
import com.zr.vo.lease.Order;

import java.util.List;

public interface CustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    Customer selectById(Long id);

    List<Order> list(Customer customer);

    void updateBycustomerId(Customer customer);

    void delAllByCustomerId(String customerId);

    Customer selectByCustomerId(Long id);

    Customer customerLogin(Customer customer);
}