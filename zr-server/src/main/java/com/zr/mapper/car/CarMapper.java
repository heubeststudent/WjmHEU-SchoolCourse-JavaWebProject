package com.zr.mapper.car;

import com.zr.vo.car.Car;

import java.util.List;

public interface CarMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Car record);

    int insertSelective(Car record);

    Car selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Car record);

    int updateByPrimaryKey(Car record);

    Car selectId(Long id);

    List<Car> selectAll();

    List<Car> list(Car car);
}