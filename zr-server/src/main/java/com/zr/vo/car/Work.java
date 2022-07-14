package com.zr.vo.car;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zr.vo.BaseVo;
import com.zr.vo.lease.Order;
import com.zr.vo.shop.Shop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Work extends BaseVo implements Serializable {
    private Long id;

    private Long orderId;

    //自定义
    private Order order;

    //客户编号
    private String customer_Id;

    //预定车型
    private String car;

    //客户姓名
    private String customerName;

    //取车门店
    private Shop pickShop;

    //还车门店
    private Shop returnShop;


    private String carNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date inTime;

    private String outUserId;

    private String inUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date outFitTime;

    private String outFitUserId;

    private String status;

    private BigDecimal total;

    private String mark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo == null ? null : carNo.trim();
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getOutUserId() {
        return outUserId;
    }

    public void setOutUserId(String outUserId) {
        this.outUserId = outUserId == null ? null : outUserId.trim();
    }

    public String getInUserId() {
        return inUserId;
    }

    public void setInUserId(String inUserId) {
        this.inUserId = inUserId == null ? null : inUserId.trim();
    }

    public Date getOutFitTime() {
        return outFitTime;
    }

    public void setOutFitTime(Date outFitTime) {
        this.outFitTime = outFitTime;
    }

    public String getOutFitUserId() {
        return outFitUserId;
    }

    public void setOutFitUserId(String outFitUserId) {
        this.outFitUserId = outFitUserId == null ? null : outFitUserId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    public String getCustomer_Id() {
        return customer_Id;
    }

    public void setCustomer_Id(String customer_Id) {
        this.customer_Id = customer_Id;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Shop getPickShop() {
        return pickShop;
    }

    public void setPickShop(Shop pickShop) {
        this.pickShop = pickShop;
    }

    public Shop getReturnShop() {
        return returnShop;
    }

    public void setReturnShop(Shop returnShop) {
        this.returnShop = returnShop;
    }
}