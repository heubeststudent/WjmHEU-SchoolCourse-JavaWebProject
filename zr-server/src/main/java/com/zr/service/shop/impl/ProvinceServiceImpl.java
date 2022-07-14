package com.zr.service.shop.impl;

import com.zr.mapper.shop.ProvinceMapper;
import com.zr.service.shop.ProvinceService;
import com.zr.vo.shop.Province;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Resource
    private ProvinceMapper provinceMapper;
    @Override
    public List<Province> proselectlist() {
        return provinceMapper.proselectlist();
    }
}
