package com.zr.mapper.sys;

import com.zr.vo.sys.UserRole;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);
}