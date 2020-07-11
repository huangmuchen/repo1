package com.xuecheng.auth.controller;

import com.xuecheng.api.auth.AuthControllerApi;
import com.xuecheng.auth.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: HuangMuChen
 * @date: 2020/7/10 21:08
 * @version: V1.0
 * @Description: 用户鉴权控制层
 */
@RestController
public class AuthController implements AuthControllerApi {
    @Autowired
    private IAuthService authService;
}