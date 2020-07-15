package com.xuecheng.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @author: HuangMuChen
 * @date: 2020/7/10 21:10
 * @version: V1.0
 * @Description: 资源服务配置类
 */
@Configuration
@EnableResourceServer // 标识这是一个资源服务
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 激活方法上的PreAuthorize注解
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String PUBLIC_KEY = "publickey.txt"; // 公钥

    /**
     * 使用jwt令牌，定义JwtTokenStore，并放入spring容器
     *
     * @param jwtAccessTokenConverter
     * @return
     */
    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /**
     * 使用jwt令牌，定义JwtAccessTokenConverter，并放入spring容器
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        // 新建一个生成token的转换器对象
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 导入证书
        converter.setVerifierKey(this.getPubKey());
        // 返回转换器
        return converter;
    }

    /**
     * 获取非对称加密公钥 Key
     *
     * @return 公钥 Key
     */
    private String getPubKey() {
        try {
            // 加载类路径下的资源
            Resource resource = new ClassPathResource(PUBLIC_KEY);
            // 获取当前资源代表的输入流
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            // 加入缓冲功能
            BufferedReader br = new BufferedReader(inputStreamReader);
            // 读取数据并返回
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            return null;
        }
    }

    /**
     * Http安全配置，对每个到达系统的http请求链接进行校验
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 所有请求必须进行用户认证
        http.authorizeRequests()
                // 下边的路径放行,如果设置为：/** ，则代表所有请求均放行
                .antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources", "/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                // 除上面url外的其他所有url都必须进行用户认证
                .anyRequest().authenticated();
    }
}