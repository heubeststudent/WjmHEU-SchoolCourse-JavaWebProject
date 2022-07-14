package com.zr.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.zr.mapper.sys.RoleMapper;
import com.zr.util.TokenUtil;
import com.zr.vo.sys.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class RoleService implements com.zr.service.sys.RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private TokenUtil tokenUtil;

    @Override
    public List<Role> selectList(Role role) {
        PageHelper.startPage(role.getPageNum(), role.getPageSize());
        return roleMapper.selectList(role);
    }

    @Override
    public String[] selectAuth(Role temp) {
        Long ddd = temp.getId();
        return roleMapper.selectAuth(ddd);
    }

    @Override
    public void createee(String name, String mark, String idList) {
        String ids[] = idList.split(" ");
        Role role = new Role();
        role.setName(name);
        role.setMark(mark);
        role.setCreateId(tokenUtil.getUserId());
        role.setCreateTime(new Date());
        roleMapper.insert(role);

        Long roleId = roleMapper.selectLastestRoleId();

        for(String id : ids){
            roleMapper.createRelation(roleId, Long.parseLong(id));
        }
    }

    @Override
    public void updateeee(String optId, String name, String mark, String idList) {
        System.out.println(idList);
        long roleId = Long.parseLong(optId);
        String ids[] = idList.split(" ");
        System.out.println(" ---------ids--------" + Arrays.toString(ids));
        System.out.println(ids.length);
        System.out.println(ids[0] == "");
        roleMapper.deleteAuthById(roleId);
        if((ids[0] != ""))
            for(String id : ids){
                roleMapper.createRelation(roleId, Long.parseLong(id));
            }
        Role role = new Role();
        role.setId(roleId);
        role.setModifyId(tokenUtil.getUserId());
        role.setModifyTime(new Date());
        role.setName(name);
        role.setMark(mark);
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteById(String id) {
        long iden = Long.parseLong(id);

        roleMapper.deleteAuthById(iden);

        roleMapper.deleteByPrimaryKey(iden);
    }

    @Override
    public List<Role> selectRoles() {
        return roleMapper.selectRoles();
    }


}
