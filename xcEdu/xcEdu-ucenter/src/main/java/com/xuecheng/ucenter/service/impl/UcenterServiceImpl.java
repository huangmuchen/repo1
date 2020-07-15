package com.xuecheng.ucenter.service.impl;

import com.xuecheng.model.domain.ucenter.XcCompanyUser;
import com.xuecheng.model.domain.ucenter.XcMenu;
import com.xuecheng.model.domain.ucenter.XcUser;
import com.xuecheng.model.domain.ucenter.ext.XcUserExt;
import com.xuecheng.ucenter.dao.mapper.XcMenuMapper;
import com.xuecheng.ucenter.dao.repository.XcCompanyUserRepository;
import com.xuecheng.ucenter.dao.repository.XcUserRepository;
import com.xuecheng.ucenter.service.IUcenterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2020/7/11 20:55
 * @version: V1.0
 * @Description: 用户中心业务层实现类
 */
@Service
public class UcenterServiceImpl implements IUcenterService {
    @Autowired
    private XcUserRepository userRepository;
    @Autowired
    private XcCompanyUserRepository companyUserRepository;
    @Autowired
    private XcMenuMapper menuMapper;

    /**
     * 根据用户名称查询用户及扩展信息
     *
     * @param username
     * @return
     */
    @Override
    public XcUserExt getUserExt(String username) {
        // 查询用户基础信息
        XcUser user = this.userRepository.findByUsername(username);
        // 判断
        if (user == null) {
            return null;
        }
        // 查询公司信息
        XcCompanyUser companyUser = this.companyUserRepository.findByUserId(user.getId());
        // 查询权限信息
        List<XcMenu> permissions = this.menuMapper.selectPermissionByUserId(user.getId());
        // 创建用户扩展类对象
        XcUserExt userExt = new XcUserExt();
        // 封装数据
        BeanUtils.copyProperties(user, userExt);
        // 判断
        if (companyUser != null) {
            // 设置公司id
            userExt.setCompanyId(companyUser.getCompanyId());
        }
        // 设置权限
        userExt.setPermissions(permissions);
        // 返回用户扩展对象
        return userExt;
    }
}