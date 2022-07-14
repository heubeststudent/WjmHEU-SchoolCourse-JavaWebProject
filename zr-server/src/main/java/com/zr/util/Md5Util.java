package com.zr.util;

import sun.misc.BASE64Encoder;
import java.security.MessageDigest;

/**
 * 密码加密类
 */
public class Md5Util {

    public static String EncoderByMd5(String str) {
        String password = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            password = base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

}
