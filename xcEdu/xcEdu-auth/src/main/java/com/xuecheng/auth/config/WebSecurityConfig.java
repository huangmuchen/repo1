package com.xuecheng.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: HuangMuChen
 * @date: 2020/7/10 21:10
 * @version: V1.0
 * @Description: 自定义配置类
 */
@Configuration
@EnableWebSecurity // Spring Security用于启用Web安全的注解,典型的用法是该注解用在某个Web安全配置类上
@Order(-1)
        // 定义了组件的加载顺序，值越小拥有越高的优先级，可为负数，'-1'保证该AOP在@Transactional之前执行
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 不用进行认证的访问路径
        web.ignoring().antMatchers("/userlogin", "/userlogout", "/userjwt");
    }

    /**
     * 创建一个认证管理器，并注入spring容器
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 采用bcrypt对密码进行编码
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * HttpSecurity对象的配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // 关闭默认打开的跨域保护
            .httpBasic().and() // 进行http Basic认证
            .formLogin().and() // 采用formLogin登录认证模式
            .authorizeRequests().anyRequest().authenticated(); // 所有的url请求都需要进行用户认证
    }
}