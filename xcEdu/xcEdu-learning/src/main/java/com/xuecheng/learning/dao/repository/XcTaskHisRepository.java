package com.xuecheng.learning.dao.repository;

import com.xuecheng.model.domain.task.XcTaskHis;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: HuangMuChen
 * @date: 2020/7/17 8:14
 * @version: V1.0
 * @Description: 历史任务Repository
 */
public interface XcTaskHisRepository extends JpaRepository<XcTaskHis,String> {
}