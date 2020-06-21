package com.xuecheng.course.service;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.course.Teachplan;
import com.xuecheng.model.domain.course.ext.TeachplanNode;
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

    /**
     * 根据课程id查询课程计划列表
     *
     * @param courseId
     * @return
     */
    TeachplanNode findTeachplanList(String courseId);

    /**
     * 添加课程计划
     *
     * @param teachplan
     * @return
     */
    ResponseResult addTeachplan(Teachplan teachplan);
}