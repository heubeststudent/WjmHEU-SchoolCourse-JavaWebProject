package com.zr.service.client;


import com.zr.mapper.lease.CustomerMapper;
import com.zr.util.Md5Util;
import com.zr.vo.lease.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {


    @Autowired
    private CustomerMapper customerMapper;

    public Customer customerLogin(Customer customer) {

        customer.setPassword(Md5Util.EncoderByMd5(customer.getPassword()));
        System.out.println(customer.getPassword());
        return customerMapper.customerLogin(customer);

    }
}
