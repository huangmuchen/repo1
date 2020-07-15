package com.xuecheng.auth.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: HuangMuChen
 * @date: 2020/7/13 10:25
 * @version: V1.0
 * @Description: BCryptPasswordEncoder对密码加随机盐，每次的Hash值都不一样，安全性高
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestBCryptPasswordEncoder {
    @Test
    public void testPasswrodEncoder() {
        String password = "111111";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        for (int i = 0; i < 10; i++) {
            // 每个计算出的Hash值都不一样
            String hashPass = passwordEncoder.encode(password);
            System.out.println(hashPass);
            // 虽然每次计算的密码Hash值不一样但是校验是通过的
            boolean f = passwordEncoder.matches(password, hashPass);
            System.out.println(f);
        }
    }
}