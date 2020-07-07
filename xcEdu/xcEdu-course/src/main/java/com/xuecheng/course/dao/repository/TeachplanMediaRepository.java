package com.xuecheng.course.dao.repository;

import com.xuecheng.model.domain.course.TeachplanMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2020/6/30 16:51
 * @version: V1.0
 * @Description: 课程计划与媒资关联Repository
 */
public interface TeachplanMediaRepository extends JpaRepository<TeachplanMedia, String> {

    /**
     * 根据课程id查询课程计划与媒资关联列表
     *
     * @param courseId
     * @return
     */
    List<TeachplanMedia> findByCourseId(String courseId);
}