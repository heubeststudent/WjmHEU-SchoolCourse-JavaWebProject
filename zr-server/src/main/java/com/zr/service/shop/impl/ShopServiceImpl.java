package com.zr.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.zr.mapper.shop.ShopMapper;
import com.zr.service.shop.ShopService;
import com.zr.util.TokenUtil;
import com.zr.vo.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService{
    @Resource
    private ShopMapper shopMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public List<Shop> list(Shop shop) {
        PageHelper.startPage(shop.getPageNum(), shop.getPageSize());
        return shopMapper.list(shop);
    }
    @Transactional
    @Override
    public void add(Shop shop) {
        shop.setCreateId(tokenUtil.getUserId());
        shop.setCreateTime(new Date());
        shopMapper.insertSelective(shop);
    }


    @Transactional
    @Override
    public void edit(Shop shop) {
        if(shop.getProvinceId() != null) {
            shop.setProvinceId(shop.getProvinceId());
            if (shop.getCityId() != null) {
                shop.setProvinceId(shop.getCityId());
            }
        }
        shop.setModifyId(tokenUtil.getUserId());
        shop.setModifyTime(new Date());
        shopMapper.updateByPrimaryKeySelective(shop);
    }
    @Transactional
    @Override
    public void del(Long id) {
        shopMapper.deleteByPrimaryKey(id);
    }
}
