package com.zr.mapper.sys;

import com.zr.vo.sys.RoleAuth;

public interface RoleAuthMapper {
    int insert(RoleAuth record);

    int insertSelective(RoleAuth record);
}