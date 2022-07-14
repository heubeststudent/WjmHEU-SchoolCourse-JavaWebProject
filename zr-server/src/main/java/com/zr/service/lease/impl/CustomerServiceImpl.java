package com.zr.service.lease.impl;

import com.github.pagehelper.PageHelper;
import com.mysql.cj.util.StringUtils;
import com.zr.mapper.lease.CustomerMapper;
import com.zr.mapper.lease.OrderMapper;
import com.zr.service.lease.CustomerService;
import com.zr.util.Md5Util;
import com.zr.util.TokenUtil;
import com.zr.vo.lease.Customer;
import com.zr.vo.lease.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private TokenUtil tokenUtil;

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private OrderMapper orderMapper;

    /**
     * 查询用户
     * @param customer
     * @return
     */
    @Override
    public List<Order> list(Customer customer) {
        //分页插件使用，放在接口方法前，只生效一次
        PageHelper.startPage(customer.getPageNum(), customer.getPageSize());
        return customerMapper.list(customer);
    }

    /**
     * 添加用户
     * @param customer
     */
    @Override
    public void add(Customer customer) {
        //对密码进行加密处理
        customer.setPassword(Md5Util.EncoderByMd5(customer.getPassword()));
        customer.setStatus("正常");
        customer.setCreateId(tokenUtil.getUserId());
        customer.setCreateTime(new Date());
        customerMapper.insertSelective(customer);
    }

    /**
     * 修改用户
     * @param customer
     */
    @Override
    public void edit(Customer customer) {
        //如果存在修改密码，则加密密码
        if(!StringUtils.isNullOrEmpty(customer.getPassword())){
            customer.setPassword(Md5Util.EncoderByMd5(customer.getPassword()));
        }
        customer.setModifyId(tokenUtil.getUserId());
        customer.setModifyTime(new Date());
        customerMapper.updateBycustomerId(customer);
    }

    /**
     * 删除用户
     * @param customerId
     */
    @Transactional
    @Override
    public void delAllByCustomerId(String customerId) {
            //删除该用户拥有的角色
            customerMapper.delAllByCustomerId(customerId);
            orderMapper.delAllByCustomerId(customerId);
        }

}
