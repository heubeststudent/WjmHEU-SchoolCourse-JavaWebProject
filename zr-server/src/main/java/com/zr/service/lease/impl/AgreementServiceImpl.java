package com.zr.service.lease.impl;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.mysql.cj.util.StringUtils;
import com.zr.mapper.lease.AgreementMapper;
import com.zr.service.lease.AgreementService;
import com.zr.util.AjaxResult;
import com.zr.util.Md5Util;
import com.zr.util.TokenUtil;
import com.zr.vo.lease.Agreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AgreementServiceImpl implements AgreementService {


    @Autowired
    private TokenUtil tokenUtil;


    @Resource
    private AgreementMapper agreementMapper;

    /**
     * 查询合同
     * @param agreement
     * @return
     */
    @Override
    public List<Agreement> list(Agreement agreement) {
        //分页插件使用，放在接口方法前，只生效一次
        PageHelper.startPage(agreement.getPageNum(), agreement.getPageSize());
        return agreementMapper.list(agreement);
    }

    /**
     * 添加合同
     * @param agreement
     */
    @Override
    public void add(Agreement agreement) {
        agreement.setCreateId(tokenUtil.getUserId());
        agreement.setCreateTime(new Date());
        agreementMapper.insertSelective(agreement);
    }

    /**
     * 修改合同
     * @param agreement
     */
    @Override
    public void edit(Agreement agreement) {
        agreement.setModifyId(tokenUtil.getUserId());
        agreement.setModifyTime(new Date());
        agreementMapper.updateByPrimaryKeySelective(agreement);
    }

    /**
     * 删除合同，通过合同名来进行删除
     * @param name
     */
    @Override
    public void delAllName(String name) {
        agreementMapper.deleteByName(name);
    }
}
