package com.xuecheng.ucenter.dao.repository;

import com.xuecheng.model.domain.ucenter.XcUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: HuangMuChen
 * @date: 2020/6/18 15:38
 * @version: V1.0
 * @Description: 用户Repository
 */
public interface XcUserRepository extends JpaRepository<XcUser, String> {

    /**
     * 根据用户名称查询用户
     *
     * @param username
     * @return
     */
    XcUser findByUsername(String username);
}