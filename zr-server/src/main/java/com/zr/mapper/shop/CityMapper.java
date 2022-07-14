package com.zr.mapper.shop;

import com.zr.vo.shop.City;

import java.util.List;

public interface CityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);

    City selectByfrom_shop_id(String id);

    City selectByto_shop_id(String id);

    List<City> list(City city);

    String selectByCityId(Long id);

    List<City> cityselectlist(Long provinceId);
}