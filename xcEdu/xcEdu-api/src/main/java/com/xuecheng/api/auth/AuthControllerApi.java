package com.xuecheng.api.auth;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.ucenter.request.LoginRequest;
import com.xuecheng.model.domain.ucenter.response.LoginResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author: HuangMuChen
 * @date: 2020/7/10 21:04
 * @version: V1.0
 * @Description: Auth(用户认证授权)相关的对外暴露的接口：在Auth服务工程编写Controller类实现此接口
 */
@Api(value = "用户认证授权管理", description = "用户认证授权管理接口，负责用户登录认证", tags = "AuthApi")
public interface AuthControllerApi {

    /**
     * 登录认证
     *
     * @param loginRequest
     * @return
     */
    @ApiOperation("登录")
    @ApiImplicitParam(name = "loginRequest", value = "登录条件", paramType = "body", required = true, dataTypeClass = LoginRequest.class)
    LoginResult login(LoginRequest loginRequest);

    /**
     * 退出认证
     *
     * @return
     */
    @ApiOperation("退出")
    ResponseResult logout();
}