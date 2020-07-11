package com.xuecheng.auth.service.impl;

import com.xuecheng.auth.client.UserClient;
import com.xuecheng.auth.pojo.UserJwt;
import com.xuecheng.model.domain.ucenter.XcMenu;
import com.xuecheng.model.domain.ucenter.ext.XcUserExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private UserClient userClient;

    /**
     * 通过用户名来加载用户：主要用于从系统数据中查询并加载具体的用户到Spring Security中
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if (authentication == null) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null) {
                // 密码
                String clientSecret = clientDetails.getClientSecret();
                return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        // 远程调用查询用户信息
        XcUserExt userExt = userClient.getUserExt(username);
        // 判断
        if (userExt == null) {
            return null;
        }
        // 从数据库获取权限
        List<XcMenu> permissions = userExt.getPermissions();
        // 判断
        if (permissions == null) {
            permissions = new ArrayList<>();
        }
        // new一个权限集合
        List<String> user_permission = new ArrayList<>();
        // 遍历权限集合
        permissions.forEach(item -> user_permission.add(item.getCode()));
        // 权限拼接
        String user_permission_string = StringUtils.join(user_permission.toArray(), ",");
        // 取出正确密码（hash值）
        String password = userExt.getPassword();
        // 拼装用户信息到UserDetails，第三个参数是用户权限
        UserJwt userDetails = new UserJwt(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList(user_permission_string));
        // 用户id
        userDetails.setId(userExt.getId());
        // 用户类型
        userDetails.setUtype(userExt.getUtype());
        // 所属企业
        userDetails.setCompanyId(userExt.getCompanyId());
        // 用户名称
        userDetails.setName(userExt.getName());
        // 用户头像
        userDetails.setUserpic(userExt.getUserpic());
        // 返回用户信息
        return userDetails;
    }
}