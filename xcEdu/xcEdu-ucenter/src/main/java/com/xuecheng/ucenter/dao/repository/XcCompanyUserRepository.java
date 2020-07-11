package com.xuecheng.ucenter.dao.repository;

import com.xuecheng.model.domain.ucenter.XcCompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: HuangMuChen
 * @date: 2020/6/18 15:38
 * @version: V1.0
 * @Description: 企业与用户关联Repository
 */
public interface XcCompanyUserRepository extends JpaRepository<XcCompanyUser, String> {

    /**
     * 根据用户id查询企业用户
     *
     * @param userId
     * @return
     */
    XcCompanyUser findByUserId(String userId);
}