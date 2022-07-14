package com.zr.service.car;

import com.zr.vo.car.Car;

import java.util.List;

public interface CarService {
    List<Car> selectList();

    List<Car> list(Car car);

    void add(Car car);

    void edit(Car car);

    void del(Long id);

    String uploadImg(String imgUrl);
}
