package com.xuecheng.course.service;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.course.CourseBase;
import com.xuecheng.model.domain.course.CourseMarket;
import com.xuecheng.model.domain.course.CoursePic;
import com.xuecheng.model.domain.course.Teachplan;
import com.xuecheng.model.domain.course.ext.CourseView;
import com.xuecheng.model.domain.course.ext.TeachplanNode;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import com.xuecheng.model.domain.course.response.AddCourseResult;

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

    /**
     * 添加课程基础信息
     *
     * @param courseBase
     * @return
     */
    AddCourseResult addCourseBase(CourseBase courseBase);

    /**
     * 查询课程基础信息
     *
     * @param courseId
     * @return
     */
    CourseBase getCourseBaseById(String courseId);

    /**
     * 更新课程基础信息
     *
     * @param courseId
     * @param courseBase
     * @return
     */
    ResponseResult updateCoursebase(String courseId, CourseBase courseBase);

    /**
     * 查询课程营销信息
     *
     * @param id
     * @return
     */
    CourseMarket getCourseMarketById(String id);

    /**
     * 更新课程营销信息
     *
     * @param id
     * @param courseMarket
     * @return
     */
    ResponseResult updateCourseMarket(String id, CourseMarket courseMarket);

    /**
     * 添加课程图片
     *
     * @param courseId
     * @param pic
     * @return
     */
    ResponseResult addCoursePic(String courseId, String pic);

    /**
     * 查询课程图片
     *
     * @param courseId
     * @return
     */
    CoursePic findCoursePic(String courseId);

    /**
     * 删除课程图片
     *
     * @param courseId
     * @return
     */
    ResponseResult deleteCoursePic(String courseId);

    /**
     * 课程数据模型查询
     *
     * @param courseId
     * @return
     */
    CourseView getCourseView(String courseId);

    /**
     * 修改课程计划
     *
     * @param teachplan
     * @return
     */
    ResponseResult updateTeachplan(Teachplan teachplan);

    /**
     * 根据课程计划id查询课程计划
     *
     * @param teachplanId
     * @return
     */
    Teachplan findTeachplan(String teachplanId);

    /**
     * 删除课程计划
     *
     * @param teachplanNode
     * @return
     */
    ResponseResult deleteTeachPlan(TeachplanNode teachplanNode);
}