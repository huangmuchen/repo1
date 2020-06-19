package com.xuecheng.course.service;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.model.domain.course.request.CourseListRequest;

/**
 * @author: HuangMuChen
 * @date: 2020/6/19 20:36
 * @version: V1.0
 * @Description: 课程信息业务层接口
 */
public interface ICourseService {

    /**
     * 分页查询课程列表
     *
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);
}