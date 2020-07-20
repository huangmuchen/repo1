package com.xuecheng.learning.dao.repository;

import com.xuecheng.model.domain.learning.XcLearningCourse;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: HuangMuChen
 * @date: 2020/7/17 8:14
 * @version: V1.0
 * @Description: 课程学习Repository
 */
public interface XcLearningCourseRepository extends JpaRepository<XcLearningCourse, String> {

    /**
     * 根据课程id和用户查询
     *
     * @param courseId
     * @param userId
     * @return
     */
    XcLearningCourse findByCourseIdAndUserId(String courseId, String userId);
}