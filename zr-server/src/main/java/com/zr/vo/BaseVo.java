package com.zr.vo;

public class BaseVo {

    //当前数据显示第pageNum页
    private int pageNum;

    //当前页面实现pageSize条数据
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
