package com.xuecheng.ucenter.dao.repository;

import com.xuecheng.model.domain.ucenter.XcCompany;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author: HuangMuChen
 * @date: 2020/6/18 15:38
 * @version: V1.0
 * @Description: 企业Repository
 */
public interface XcCompanyRepository extends JpaRepository<XcCompany, String> {
}