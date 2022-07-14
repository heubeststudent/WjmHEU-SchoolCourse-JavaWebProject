package com.zr.service.sys;

import com.zr.vo.sys.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<User> list(User user);

    void add(User user);

    User login(User user);

    List<String> selectAuthUrlsList(String userId);

    String loadImgByImgUrl(String imgUrl);

    void logout();

    void uploadImg(MultipartFile file) throws IOException;

    String loadImg();

    int modify(User user);

    void del(String userId);
}
