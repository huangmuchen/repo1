package com.xuecheng.course.dao.repository;

import com.xuecheng.model.domain.course.CoursePub;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: HuangMuChen
 * @date: 2020/6/30 16:51
 * @version: V1.0
 * @Description: 课程发布Repository
 */
public interface CoursePubRepository extends JpaRepository<CoursePub, String> {

}