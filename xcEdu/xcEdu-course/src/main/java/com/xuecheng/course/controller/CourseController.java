package com.xuecheng.course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.course.service.ICourseService;
import com.xuecheng.model.domain.course.CourseBase;
import com.xuecheng.model.domain.course.CourseMarket;
import com.xuecheng.model.domain.course.Teachplan;
import com.xuecheng.model.domain.course.ext.TeachplanNode;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import com.xuecheng.model.domain.course.response.AddCourseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 根据课程id查询课程计划列表
     *
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        // 调用Service层进行查询,并返回结果
        return this.courseService.findTeachplanList(courseId);
    }

    /**
     * 添加课程计划
     *
     * @param teachplan
     * @return
     */
    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) { // Vue使用axios请求数据，默认post请求传参是json字符串格式
        // 调用Service层进行添加,并返回添加结果
        return this.courseService.addTeachplan(teachplan);
    }

    /**
     * 添加课程基础信息
     *
     * @param courseBase
     * @return
     */
    @Override
    @PostMapping("/coursebase/add")
    public AddCourseResult addCourseBase(@RequestBody CourseBase courseBase) {
        // 调用service层进行添加
        return this.courseService.addCourseBase(courseBase);
    }

    /**
     * 查询课程基础信息
     *
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/courseview/get/{courseId}")
    public CourseBase getCourseBaseById(@PathVariable("courseId") String courseId) {
        // 调用service层进行查询
        return this.courseService.getCourseBaseById(courseId);
    }

    /**
     * 更新课程基础信息
     *
     * @param courseId
     * @param courseBase
     * @return
     */
    @Override
    @PutMapping("/coursebase/update/{courseId}")
    public ResponseResult updateCourseBase(@PathVariable("courseId") String courseId, @RequestBody CourseBase courseBase) {
        // 调用service层进行更新
        return this.courseService.updateCoursebase(courseId, courseBase);
    }

    /**
     * 查询课程营销信息
     *
     * @param id
     * @return
     */
    @Override
    @GetMapping("/coursemarket/get/{id}")
    public CourseMarket getCourseMarketById(@PathVariable("id") String id) {
        // 调用service层进行查询
        return this.courseService.getCourseMarketById(id);
    }

    /**
     * 更新课程营销信息
     *
     * @param id
     * @param courseMarket
     * @return
     */
    @Override
    @PutMapping("/coursemarket/update/{id}")
    public ResponseResult updateCourseMarket(@PathVariable("id") String id, @RequestBody CourseMarket courseMarket) {
        // 调用service层进行更新
        return this.courseService.updateCourseMarket(id, courseMarket);
    }
}