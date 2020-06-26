package com.xuecheng.course.service;

import com.xuecheng.model.domain.course.response.CoursePublishResult;

/**
 * @author: HuangMuChen
 * @date: 2020/6/26 23:31
 * @version: V1.0
 * @Description: 课程页面业务层接口
 */
public interface ICoursePageService {

    /**
     * 课程预览
     *
     * @param courseId
     * @return
     */
    CoursePublishResult coursePreview(String courseId);
}