package com.zr.mapper.sys;

import com.zr.vo.sys.Auth;

public interface AuthMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Auth record);

    int insertSelective(Auth record);

    Auth selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    Auth[] selectParents();

    Auth[] selectChildren();

    void update(Auth auth);


}