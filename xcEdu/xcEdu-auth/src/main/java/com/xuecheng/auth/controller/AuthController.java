package com.xuecheng.auth.controller;

import com.xuecheng.api.auth.AuthControllerApi;
import com.xuecheng.auth.config.AuthProperties;
import com.xuecheng.auth.service.IAuthService;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.ucenter.ext.AuthToken;
import com.xuecheng.model.domain.ucenter.request.LoginRequest;
import com.xuecheng.model.domain.ucenter.response.LoginResult;
import com.xuecheng.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: HuangMuChen
 * @date: 2020/7/10 21:08
 * @version: V1.0
 * @Description: 用户认证与授权控制层
 */
@RestController
@EnableConfigurationProperties(AuthProperties.class)
public class AuthController implements AuthControllerApi {
    @Autowired
    private AuthProperties prop;
    @Autowired
    private IAuthService authService;

    /**
     * 登录认证
     *
     * @param loginRequest
     * @return
     */
    @Override
    @PostMapping("/userlogin")
    public LoginResult login(LoginRequest loginRequest) {
        // 调用service层进行登录认证
        AuthToken authToken = this.authService.login(loginRequest);
        // 获取令牌
        String token = authToken.getJti_token();
        // 将令牌保存到cookie中
        saveCookie(token);
        // 返回认证结果
        return new LoginResult(CommonCode.SUCCESS, token);
    }

    /**
     * 退出认证
     *
     * @return
     */
    @Override
    @PostMapping("/userlogout")
    public ResponseResult logout() {
        // 根据cookieName获取cookie中的令牌
        String token = getToken(prop.getCookieName());
        // 调用service层进行退出认证
        this.authService.logout(token);
        // 删除cookie
        deleteCookie(token);
        // 返回删退出结果
        return ResponseResult.SUCCESS();
    }

    /**
     * 删除cookie中的令牌
     *
     * @param token
     */
    public void deleteCookie(String token) {
        // SpringMVC中直接获取HttpServletResponse对象
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        // 将cookie的存活时间设置为 0 即代表删除
        CookieUtil.addCookie(response, prop.getCookieDomain(), "/", prop.getCookieName(), token, 0, false);
    }

    /**
     * 从cookie中获取令牌
     *
     * @return
     */
    public String getToken(String cookieName) {
        // SpringMVC中直接获取HttpServletRequest对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 从cookie中获取令牌
        Map<String, String> map = CookieUtil.readCookie(request, cookieName);
        // 判断
        if (CollectionUtils.isEmpty(map) || StringUtils.isBlank(map.get(cookieName))) {
            return null;
        }
        // 返回令牌
        return map.get(cookieName);
    }

    /**
     * 将令牌保存到cookie中
     *
     * @param token
     */
    public void saveCookie(String token) {
        // SpringMVC中直接获取HttpServletResponse对象
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        // 将令牌保存到cookie中，并返回给浏览器
        CookieUtil.addCookie(response, prop.getCookieDomain(), "/", prop.getCookieName(), token, prop.getCookieMaxAge(), false);
    }
}