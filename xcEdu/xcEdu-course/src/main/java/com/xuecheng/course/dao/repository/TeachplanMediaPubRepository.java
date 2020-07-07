package com.xuecheng.course.dao.repository;

import com.xuecheng.model.domain.course.TeachplanMediaPub;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: HuangMuChen
 * @date: 2020/6/30 16:51
 * @version: V1.0
 * @Description: 课程计划与媒资关联发布Repository
 */
public interface TeachplanMediaPubRepository extends JpaRepository<TeachplanMediaPub, String> {

    /**
     * 根据课程id删除课程计划与媒资关联发布信息
     *
     * @param courseId
     * @return
     */
    Integer deleteByCourseId(String courseId);
}