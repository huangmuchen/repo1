package com.xuecheng.gateway.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: HuangMuChen
 * @date: 2020/7/13 21:32
 * @version: V1.0
 * @Description: 网关业务层接口
 */
public interface IZuulService {

    /**
     * 从Cookie获取身份令牌
     *
     * @param request
     * @return
     */
    String getJtiTokenFromCookie(HttpServletRequest request);

    /**
     * 从http header查询jwt令牌
     *
     * @param request
     * @return
     */
    String getJwtTokenFromHeader(HttpServletRequest request);

    /**
     * 查询令牌的有效期
     *
     * @param jti_token
     * @return
     */
    long getExpire(String jti_token);
}