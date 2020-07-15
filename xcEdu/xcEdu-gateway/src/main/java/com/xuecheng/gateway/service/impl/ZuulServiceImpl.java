package com.xuecheng.gateway.service.impl;

import com.xuecheng.gateway.config.FilterProperties;
import com.xuecheng.gateway.service.IZuulService;
import com.xuecheng.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: HuangMuChen
 * @date: 2020/7/13 21:33
 * @version: V1.0
 * @Description: 网关业务层实现类
 */
@Service
@EnableConfigurationProperties(FilterProperties.class)
public class ZuulServiceImpl implements IZuulService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private FilterProperties prop;

    /**
     * 从Cookie获取身份令牌
     *
     * @param request
     * @return
     */
    public String getJtiTokenFromCookie(HttpServletRequest request) {
        // 从cookie中获取身份令牌
        Map<String, String> map = CookieUtil.readCookie(request, prop.getCookieName());
        String jti_token = map.get(prop.getCookieName());
        // 判断
        if (StringUtils.isBlank(jti_token)) {
            return null;
        }
        // 返回身份令牌
        return jti_token;
    }

    /**
     * 从http header查询jwt令牌
     *
     * @param request
     * @return
     */
    @Override
    public String getJwtTokenFromHeader(HttpServletRequest request) {
        // 取出头信息
        String authorization = request.getHeader("Authorization");
        // 判断
        if (StringUtils.isBlank(authorization)) {
            return null;
        } else if (!authorization.startsWith("Bearer ")) {
            return null;
        } else {
            // 取出jwt令牌返回
            return authorization.substring(7);
        }
    }

    /**
     * 查询令牌的有效期
     *
     * @param jti_token
     * @return
     */
    @Override
    public long getExpire(String jti_token) {
        // 拼接key
        String key = prop.getPrefixKey() + jti_token;
        // 查询令牌过期时间,并返回
        return this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}