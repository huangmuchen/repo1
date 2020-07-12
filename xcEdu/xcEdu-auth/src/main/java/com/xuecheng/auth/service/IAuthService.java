package com.xuecheng.auth.service;

import com.xuecheng.model.domain.ucenter.ext.AuthToken;
import com.xuecheng.model.domain.ucenter.request.LoginRequest;

/**
 * @author: HuangMuChen
 * @date: 2020/7/10 21:10
 * @version: V1.0
 * @Description: 用户鉴权业务层接口
 */
public interface IAuthService {

    /**
     * 登录认证
     *
     * @param loginRequest
     * @return
     */
    AuthToken login(LoginRequest loginRequest);

    /**
     * 退出认证
     *
     * @return
     */
    void logout(String token);
}