package com.xuecheng.course.controller;

import com.xuecheng.api.course.CoursePageControllerApi;
import com.xuecheng.course.service.ICoursePageService;
import com.xuecheng.model.domain.course.response.CoursePublishResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: HuangMuChen
 * @date: 2020/6/26 23:24
 * @version: V1.0
 * @Description: 课程页面控制层
 */
@RestController
@RequestMapping("/course")
public class CoursePageController implements CoursePageControllerApi {
    @Autowired
    private ICoursePageService coursePageService;

    /**
     * 课程预览
     *
     * @param courseId
     * @return
     */
    @Override
    @PostMapping("/preview/{courseId}")
    public CoursePublishResult coursePreview(@PathVariable("courseId") String courseId) {
        // 调用service层进行课程预览
        return this.coursePageService.coursePreview(courseId);
    }

    /**
     * 课程发布
     *
     * @param courseId
     * @return
     */
    @Override
    @PostMapping("/publish/{courseId}")
    public CoursePublishResult coursePublish(@PathVariable("courseId") String courseId) {
        // 调用service层进行课程发布
        return this.coursePageService.coursePublish(courseId);
    }
}