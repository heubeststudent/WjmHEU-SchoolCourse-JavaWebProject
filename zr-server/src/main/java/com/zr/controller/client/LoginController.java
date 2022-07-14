package com.zr.controller.client;


import com.alibaba.fastjson.JSON;
import com.zr.service.client.LoginService;
import com.zr.util.AjaxResult;
import com.zr.util.RedisUtils;
import com.zr.vo.lease.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequestMapping("client")
@RestController
public class LoginController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private LoginService loginService;

    @RequestMapping("/customerLogin")
    public String customerLogin(@RequestBody Customer customer) {
        Customer loginCustomer = loginService.customerLogin(customer);
        AjaxResult ajaxResult = null;
        if(loginCustomer != null){
            //生成Token(Jwt)
            String token = UUID.randomUUID().toString();
            //把token放入redis缓存，有效时间设置为15分钟
            redisUtils.set(token, loginCustomer, 3L, TimeUnit.DAYS);
            Map dataMap = new HashMap();
            dataMap.put("loginCustomerName", loginCustomer.getName());
            ajaxResult = new AjaxResult(true, token, dataMap);
        }else{
            ajaxResult = new AjaxResult(false, "账号或密码错误");
        }
        return JSON.toJSONString(ajaxResult);
    }

}
