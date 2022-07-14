package com.zr.mapper.sys;

import com.zr.vo.sys.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> list(User user);

    User login(User user);

    List<String> selectAuthUrlsList(String userId);

    int modifySelective(User user);
}