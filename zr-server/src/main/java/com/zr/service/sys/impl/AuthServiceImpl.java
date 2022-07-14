package com.zr.service.sys.impl;

import com.zr.mapper.sys.AuthMapper;
import com.zr.service.sys.AuthService;
import com.zr.util.TokenUtil;
import com.zr.vo.sys.Auth;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 **/
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private AuthMapper authMapper;

    @Resource
    private TokenUtil tokenUtil;


    @Override
    public Auth[] selectParents() {
        return authMapper.selectParents();
    }

    @Override
    public Auth[] selectChildren() {
        return authMapper.selectChildren();
    }

    @Override
    public Auth selectById(Long idT) {
        return authMapper.selectByPrimaryKey(idT);
    }

    @Override
    public void update(Long idT, String name, String mark, String seq) {
        Auth auth = new Auth();
        auth.setName(name);
        auth.setId(idT);
        auth.setMark(mark);
        auth.setSeq(Short.parseShort(seq));
        auth.setModifyTime(new Date());
        auth.setModifyId(tokenUtil.getUserId());
        authMapper.update(auth);
    }

    @Override
    public void delete(Long idT) {
        authMapper.deleteByPrimaryKey(idT);
    }

    @Override
    public void create(Auth auth) {
        auth.setCreateId(tokenUtil.getUserId());
        authMapper.insertSelective(auth);
    }
}
