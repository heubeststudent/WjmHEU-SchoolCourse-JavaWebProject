package com.zr.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.zr.mapper.shop.CityMapper;
import com.zr.mapper.shop.ShopMapper;
import com.zr.service.shop.CityService;
import com.zr.util.RedisUtils;
import com.zr.util.TokenUtil;
import com.zr.vo.shop.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class CityServiceImpl implements CityService{
    @Autowired
    private RedisUtils redisUtils;
    @Resource
    private ShopMapper shopMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Resource
    private CityMapper cityMapper;

    /**
     * 查询城市
     * @param city
     * @return
     */
    @Override
    public List<City> list(City city) {
        PageHelper.startPage(city.getPageNum(), city.getPageSize());
        return cityMapper.list(city);
    }

    @Transactional
    @Override
    public void add(City city) {
        city.setCreateId(tokenUtil.getUserId());
        city.setCreateTime(new Date());
        city.setProvinceId(city.getProvince());
        cityMapper.insertSelective(city);
    }
    @Transactional
    @Override
    public void edit(City city) {
        //判断是否有省份修改
        if(city.getProvince() != null){
            city.setProvinceId(city.getProvince());
        }
        city.setModifyId(tokenUtil.getUserId());
        city.setModifyTime(new Date());
        cityMapper.updateByPrimaryKeySelective(city);
    }
    @Transactional
    @Override
    public void del(Long id) {
        shopMapper.deleteByCityId(id);
        cityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<City> cityselectlist(Long provinceId) {
        return cityMapper.cityselectlist(provinceId);
    }

}
