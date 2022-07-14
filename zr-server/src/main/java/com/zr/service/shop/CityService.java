package com.zr.service.shop;

import com.zr.vo.shop.City;

import java.util.List;

public interface CityService {
    List<City> list(City city);

    void add(City city);

    void edit(City city);

    void del(Long id);

    List<City> cityselectlist(Long provinceId);
}
