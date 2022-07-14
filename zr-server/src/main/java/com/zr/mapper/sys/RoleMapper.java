package com.zr.mapper.sys;

import com.zr.vo.sys.Role;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectList(Role role);

    List<Role> listByUserId(String userId);

    String[] selectAuth(Long id);

    Long selectLastestRoleId();

    void createRelation(Long roleId, Long id);

    void deleteAuthById(long roleId);

    List<Role> selectRoles();
}