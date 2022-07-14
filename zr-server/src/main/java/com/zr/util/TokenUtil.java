package com.zr.util;

import com.zr.vo.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录信息类
 */
@Component
public class TokenUtil {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取当前用户的token
     * @return
     */
    public String getToken(){
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        return request.getHeader("Token");
    }


    /**
     * 获取当前登录人对象
     * @return
     */
    public User getLoginUser(){
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String token = request.getHeader("Token");
        if(token == null) return new User();
        return (User)redisUtils.get(token);
    }

    /**
     * 获取当前登录人账号
     * @return
     */
    public String getUserId(){
        return this.getLoginUser().getUserId();
    }

}
