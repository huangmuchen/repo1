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

}