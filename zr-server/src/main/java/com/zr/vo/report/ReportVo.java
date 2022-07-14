package com.zr.vo.report;

import com.zr.vo.BaseVo;

import java.io.Serializable;

public class ReportVo extends BaseVo implements Serializable {

    // 报表字段名称如车辆类型获取年月日
    private String name;
    // 报表值
    private String value;

    // 查询条件开始时间
    private String startDate;
    // 查询条件截止时间
    private String endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
