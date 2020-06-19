package com.xuecheng.course.dao.repository;

import com.xuecheng.model.domain.course.CourseBase;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: HuangMuChen
 * @date: 2020/6/18 15:38
 * @version: V1.0
 * @Description: 课程基本信息Repository
 */
public interface CourseBaseRepository extends JpaRepository<CourseBase, String> {

}