package com.zr.mapper.shop;

import com.zr.vo.shop.Shop;

import java.util.List;

public interface ShopMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);

    Shop selectByfrom_shop_id(String id);

    Shop selectByto_shop_id(String id);

    List<Shop> list(Shop shop);

    void deleteByCityId(Long id);
}