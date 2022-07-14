package com.zr.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.mysql.cj.util.StringUtils;
import com.zr.mapper.sys.UserMapper;
import com.zr.mapper.sys.UserRoleMapper;
import com.zr.service.sys.UserService;
import com.zr.util.Md5Util;
import com.zr.util.RedisUtils;
import com.zr.util.TokenUtil;
import com.zr.vo.sys.User;
import com.zr.vo.sys.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final String HEAD_FILE_DIR = "E:\\TMP\\img\\head\\";

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TokenUtil tokenUtil;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapp;

    /**
     * 查询用户
     * @param user
     * @return
     */
    @Override
    public List<User> list(User user) {
        //分页插件使用，放在接口方法前，只生效一次
        PageHelper.startPage(user.getPageNum(), user.getPageSize());
        return userMapper.list(user);
    }

    /**
     * 新增用户
     * @param user
     */
    @Transactional
    @Override
    public void add(User user) {
        //对密码进行加密处理
        user.setPassword(Md5Util.EncoderByMd5(user.getPassword()));
        user.setCreateId(tokenUtil.getUserId());
        user.setStatus("1");
        user.setCreateTime(new Date());
        if(user.getRoles() != null){
            for(Long roleId : user.getRoles()){
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(user.getUserId());
                userRoleMapp.insert(userRole);
            }
        }
        userMapper.insertSelective(user);
    }

    /**
     * 用户登录
     * @param user
     * @return String
     */
    @Override
    public User login(User user) {
        user.setPassword(Md5Util.EncoderByMd5(user.getPassword()));
        return userMapper.login(user);
    }

    /**
     * 根据账号获取权限列表
     * @param userId
     * @return
     */
    @Override
    public List<String> selectAuthUrlsList(String userId) {
        return userMapper.selectAuthUrlsList(userId);
    }

    /**
     * 根据文件地址生成base64编码
     * @param imgUrl
     * @return
     */
    @Override
    public String loadImgByImgUrl(String imgUrl) {
        if(StringUtils.isNullOrEmpty(imgUrl)) return "";
        byte[] b = new byte[0];
        File file = new File(imgUrl);
        try(FileInputStream fis = new FileInputStream(file)){
            b =  new byte[(int)file.length()];
            fis.read(b);
        }catch (Exception e){}
        return Base64.getEncoder().encodeToString(b);
    }

    /**
     * 退出系统
     */
    @Override
    public void logout() {
        redisUtils.remove(tokenUtil.getToken());
    }

    /**
     * 头像上传
     * @return String
     */
    @Override
    public void uploadImg(MultipartFile file) throws IOException {
        String extensionName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String path = HEAD_FILE_DIR + UUID.randomUUID().toString() + extensionName;
        File imgFile = new File(path);
        file.transferTo(imgFile);
        User user = userMapper.selectByPrimaryKey(tokenUtil.getUserId());
        user.setImgUrl(path);
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 头像下载
     * @return String
     */
    @Override
    public String loadImg() {
        User user = userMapper.selectByPrimaryKey(tokenUtil.getUserId());
        return loadImgByImgUrl(user.getImgUrl());
    }

    @Override
    public int modify(User user) {
        User u = new User();
        u.setUserId(user.getUserId());
        u.setStatus(user.getStatus());
        u.setModifyId(tokenUtil.getUserId());
        u.setModifyTime(new Date());
        return userMapper.modifySelective(u);
    }
    @Override
    public void del(String userId){
        userMapper.deleteByPrimaryKey(userId);
    }
}
