package com.xuecheng.ucenter.dao.repository;

import com.xuecheng.model.domain.ucenter.XcUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author: HuangMuChen
 * @date: 2020/6/18 15:38
 * @version: V1.0
 * @Description: 用户角色关联Repository
 */
public interface XcUserRoleRepository extends JpaRepository<XcUserRole, String> {
    List<XcUserRole> findXcUserRoleByUserId(String userId);
}