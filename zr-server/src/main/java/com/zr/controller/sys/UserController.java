package com.zr.controller.sys;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zr.service.sys.UserService;
import com.zr.util.AjaxResult;
import com.zr.util.RedisUtils;
import com.zr.vo.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用户操作类
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("/change")
    public String modify(@RequestBody User user){
        int result = userService.modify(user);
        if(result > 0)
            return JSON.toJSONString(AjaxResult.success("修改状态成功"));
        else
            return JSON.toJSONString(AjaxResult.success("修改状态失败"));
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public String login(@RequestBody User user){
        User loginUser = userService.login(user);
        AjaxResult ajaxResult = null;
        if(loginUser != null){
            //根据登录账号获取该用户对应的菜单权限（组件权限）
            List<String> authUrlsList = userService.selectAuthUrlsList(loginUser.getUserId());
            //生成Token(Jwt)
            String token = UUID.randomUUID().toString();
            //把token放入redis缓存，有效时间设置为15分钟
            redisUtils.set(token, loginUser, 15L, TimeUnit.MINUTES);
            Map dataMap = new HashMap();
            dataMap.put("loginUserName", loginUser.getUserName());
            dataMap.put("authUrlsList", authUrlsList);
            dataMap.put("base64", userService.loadImgByImgUrl(loginUser.getImgUrl()));
            ajaxResult = new AjaxResult(true, token, dataMap);
        }else{
            ajaxResult = new AjaxResult(false, "账号或密码错误");
        }
        return JSON.toJSONString(ajaxResult);
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping("/logout")
    public String logout(){
        userService.logout();
        return JSON.toJSONString(AjaxResult.success("退出成功"));
    }

    /**
     * 查询用户列表
     * @param user
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestBody  User user){
        //查询用户列表
        List<User> userList = userService.list(user);
        //封装到分页对象中
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return JSON.toJSONString(AjaxResult.success("查询成功", pageInfo));
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping("/add")
    public String add(@RequestBody  User user){
        userService.add(user);
        return JSON.toJSONString(AjaxResult.success("新增成功"));
    }

    /**
     * 头像上传
     * @return String
     */
    @RequestMapping("/uploadImg")
    public String uploadImg(MultipartFile file) throws Exception {
        userService.uploadImg(file);
        String base64 = userService.loadImg();
        return JSON.toJSONString(AjaxResult.success("上传成功",base64));
    }

    @RequestMapping("/del/{userId}")
    public String del(@PathVariable("userId") String userId){
        userService.del(userId);
        return JSON.toJSONString(AjaxResult.success("删除成功"));
    }

}
