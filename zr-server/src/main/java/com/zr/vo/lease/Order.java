package com.zr.vo.lease;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zr.vo.BaseVo;
import com.zr.vo.car.Car;
import com.zr.vo.shop.City;
import com.zr.vo.shop.Shop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order extends BaseVo implements Serializable {
    private Long id;

    private String customerId;

    private Long carId;

    private Long fromShopId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date fromTime;

    private Long toShopId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date toTime;

    private String agreementId;

    private String nomp;

    private String psur;

    private BigDecimal total;

    private String status;

    private String type;

    private String cardId;

    private Date payTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    //租赁车的车型订单管理使用
    private Car car;

    //客户
    private Customer cust;

    //客户姓名
    private String customerName;

    //取车门店
    private Shop pickShop;

    //还车门店
    private Shop returnShop;

    //取车城市
    private City pickCity;

    //还车城市
    private City returnCity;

    //车型列表
    private List<Car> carList;

    //车名
    private String CarName;

    //合同名
    private String Agreement;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    //加入customer属性
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getFromShopId() {
        return fromShopId;
    }

    public void setFromShopId(Long fromShopId) {
        this.fromShopId = fromShopId;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Long getToShopId() {
        return toShopId;
    }

    public void setToShopId(Long toShopId) {
        this.toShopId = toShopId;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId == null ? null : agreementId.trim();
    }

    public String getNomp() {
        return nomp;
    }

    public void setNomp(String nomp) {
        this.nomp = nomp == null ? null : nomp.trim();
    }

    public String getPsur() {
        return psur;
    }

    public void setPsur(String psur) {
        this.psur = psur == null ? null : psur.trim();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCust() {
        return cust;
    }

    public void setCust(Customer cust) {
        this.cust = cust;
    }

    public Shop getReturnShop() {
        return returnShop;
    }

    public void setReturnShop(Shop returnShop) {
        this.returnShop = returnShop;
    }

    public Shop getPickShop() {
        return pickShop;
    }

    public void setPickShop(Shop pickShop) {
        this.pickShop = pickShop;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public City getPickCity() {
        return pickCity;
    }

    public void setPickCity(City pickCity) {
        this.pickCity = pickCity;
    }

    public City getReturnCity() {
        return returnCity;
    }

    public void setReturnCity(City returnCity) {
        this.returnCity = returnCity;
    }

    public String getCarName() {
        return CarName;
    }

    public void setCarName(String carName) {
        CarName = carName;
    }

    public String getAgreement() {
        return Agreement;
    }

    public void setAgreement(String agreement) {
        Agreement = agreement;
    }
}