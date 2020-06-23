package com.xuecheng.course.dao.repository;

import com.xuecheng.model.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: HuangMuChen
 * @date: 2020/6/18 15:38
 * @version: V1.0
 * @Description: 课程图片Repository
 */
public interface CoursePicRepository extends JpaRepository<CoursePic, String> {

    /**
     * 删除成功返回1否则返回0
     *
     * @param courseId
     * @return
     */
    long deleteByCourseid(String courseId);
}