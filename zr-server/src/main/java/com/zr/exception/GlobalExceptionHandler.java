package com.zr.exception;

import com.alibaba.fastjson.JSON;
import com.zr.util.AjaxResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * controller层全局异常处理器
 * controller层不需要捕捉异常，由该全局异常处理器捕捉异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {

        @ResponseBody
        @ExceptionHandler(value = Exception.class)
        public String exceptionHandler(HttpServletResponse response, Exception e){
            //打印具体的异常信息
            e.printStackTrace();
            //设置浏览器相应代码为500
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            //返回具体的异常信息
            return JSON.toJSONString(new AjaxResult(false, "操作失败:" + e.getMessage()));
        }

}
