package com.zr.service.shop;

import com.zr.vo.shop.Shop;

import java.util.List;

public interface ShopService {
    List<Shop> list(Shop shop);

    void add(Shop shop);

    void edit(Shop shop);

    void del(Long id);
}
