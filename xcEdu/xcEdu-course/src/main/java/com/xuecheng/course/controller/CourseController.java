package com.xuecheng.course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.course.service.ICourseService;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: HuangMuChen
 * @date: 2020/6/19 20:33
 * @version: V1.0
 * @Description: 课程信息控制层
 */
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {
    @Autowired
    private ICourseService courseService;

    /**
     * 分页查询课程列表
     *
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseList(@PathVariable("page") int page, @PathVariable("size") int size, CourseListRequest courseListRequest) {
        // 调用Service层进行查询,并返回结果
        return this.courseService.findCourseList(page, size, courseListRequest);
    }
}