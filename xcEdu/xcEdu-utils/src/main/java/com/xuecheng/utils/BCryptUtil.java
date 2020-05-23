package com.xuecheng.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 对用户输入的密码进行md5加密，然后在md5基础上进行BCript加密
 */
public class BCryptUtil {
    public static String encode(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public static boolean matches(String password, String hashPass) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashPass);
    }
}