package com.zr.service.sys;

import com.zr.vo.sys.Role;

import java.util.List;


public interface RoleService {
    List<Role> selectList(Role role);

    String[] selectAuth(Role temp);

    void createee(String name, String mark, String idList);

    void updateeee(String optId, String name, String mark, String idList);

    void deleteById(String id);

    List<Role> selectRoles();

}
