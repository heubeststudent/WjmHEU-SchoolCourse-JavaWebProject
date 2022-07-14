package com.zr.util;

public class AjaxResult {

    //本次操作结果 true： 成功 false 失败
    private boolean success;

    //后端操作结果返回给前端的具体消息
    private String msg;

    //其它需要返回给前端的对象
    private Object obj;

    public AjaxResult(){}

    public AjaxResult(boolean success, String msg){
        this.success =  success;
        this.msg = msg;
    }

    public AjaxResult(boolean success, String msg, Object obj){
        this.success =  success;
        this.msg = msg;
        this.obj = obj;
    }

    /**
     * 操作成功方法，一般用于新增、修改、删除返回数据
     * @param msg
     * @return
     */
    public static AjaxResult success(String msg){
        return new AjaxResult(true, msg);
    }

    /**
     * 操作成功方法，一般用于查询列表返回数据
     * @param msg
     * @param obj
     * @return
     */
    public static  AjaxResult success(String msg, Object obj){
        return new AjaxResult(true,msg,obj);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
