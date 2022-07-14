package com.zr.interceptor;

import com.alibaba.fastjson.JSON;
import com.zr.util.AjaxResult;
import com.zr.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头中的Token
        String token = request.getHeader("Token");
        if(token != null){
            Object obj = redisUtils.get(token);
            if(obj != null){
                redisUtils.set(token, obj, 15L, TimeUnit.DAYS);
                return true;
            }
        }
        //处理Token不合法情况
        AjaxResult ajaxResult = new AjaxResult(false, "用户未登录");
        //设置响应代码401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out =  response.getWriter();
        out.write(JSON.toJSONString(ajaxResult));
        out.flush();
        return false;
    }
}
