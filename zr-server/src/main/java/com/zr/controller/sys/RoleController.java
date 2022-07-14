package com.zr.controller.sys;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zr.service.sys.RoleService;
import com.zr.util.AjaxResult;
import com.zr.vo.sys.Role;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    //获取所有角色列表
    @RequestMapping("/list")
    public String selectList(@RequestBody Role role){
        List<Role> list = roleService.selectList(role);
        for(Role temp : list){
            String[] s = roleService.selectAuth(temp);
            temp.setAuths(s);
        }
        PageInfo<Role> pageInfo = new PageInfo<>(list);
        return JSON.toJSONString(AjaxResult.success("查询成功", pageInfo));
    }


    @RequestMapping("/create")
    public String createqwe(@RequestParam("name") String name,@RequestParam("mark") String mark, @RequestParam("idList") String idList){

        roleService.createee(name, mark, idList);

        return JSON.toJSONString(AjaxResult.success("查询成功", null));
    }

    @RequestMapping("/update")
    public String updateqwe(@RequestParam("id") String optId,@RequestParam("name") String name,@RequestParam("mark") String mark, @RequestParam("idList") String idList){

        roleService.updateeee(optId, name, mark, idList);

        return JSON.toJSONString(AjaxResult.success("查询成功", null));
    }

    @RequestMapping("/delete")
    public String deleteById(@RequestParam("id") String id){

        roleService.deleteById(id);

        return JSON.toJSONString(AjaxResult.success("删除成功", null));
    }
    @RequestMapping("/selectList")
    public String roleList() {
        return JSON.toJSONString(AjaxResult.success("查询成功",roleService.selectRoles()));
    }


}
