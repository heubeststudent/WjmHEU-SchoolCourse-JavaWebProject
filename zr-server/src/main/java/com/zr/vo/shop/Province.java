package com.zr.vo.shop;

public class Province {
    //门店所属省份名称
    private Province provinceName;

    //门店所属城市名称
    private City cityName;
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Province getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(Province provinceName) {
        this.provinceName = provinceName;
    }

    public City getCityName() {
        return cityName;
    }

    public void setCityName(City cityName) {
        this.cityName = cityName;
    }
}