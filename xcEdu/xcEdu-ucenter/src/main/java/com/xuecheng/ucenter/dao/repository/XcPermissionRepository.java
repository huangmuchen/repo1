package com.xuecheng.ucenter.dao.repository;

import com.xuecheng.model.domain.ucenter.XcPermission;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author: HuangMuChen
 * @date: 2020/6/18 15:38
 * @version: V1.0
 * @Description: 角色权限关联Repository
 */
public interface XcPermissionRepository extends JpaRepository<XcPermission, String> {
}