package com.xuecheng.course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.web.BaseController;
import com.xuecheng.course.service.ICourseService;
import com.xuecheng.model.domain.course.*;
import com.xuecheng.model.domain.course.ext.CourseView;
import com.xuecheng.model.domain.course.ext.TeachplanNode;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import com.xuecheng.model.domain.course.response.AddCourseResult;
import com.xuecheng.utils.XcOauth2Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author: HuangMuChen
 * @date: 2020/6/19 20:33
 * @version: V1.0
 * @Description: 课程信息控制层
 */
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController implements CourseControllerApi {
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
        // 通过工具类获取Jwt中的用户信息
        XcOauth2Util.UserJwt userJwt = XcOauth2Util.getUserJwtFromHeader(request);
        // 取出机构id
        String companyId = userJwt.getCompanyId();
        // 调用Service层进行查询,并返回结果
        return this.courseService.findCourseList(page, size, companyId,courseListRequest);
    }

    /**
     * 根据课程id查询课程计划列表
     *
     * @param courseId
     * @return
     */
    @PreAuthorize("hasAuthority('xc_teachmanager_course_plan_list')")
    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        // 调用Service层进行查询,并返回结果
        return this.courseService.findTeachplanList(courseId);
    }

    /**
     * 根据课程计划id查询课程计划
     *
     * @param teachplanId
     * @return
     */
    @Override
    @GetMapping("/teachplan/get/{teachplanId}")
    public Teachplan findTeachplan(@PathVariable("teachplanId") String teachplanId) {
        // 调用Service层进行查询,并返回结果
        return this.courseService.findTeachplan(teachplanId);
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
     * 修改课程计划
     *
     * @param teachplan
     * @return
     */
    @Override
    @PutMapping("/teachplan/edit")
    public ResponseResult updateTeachplan(@RequestBody Teachplan teachplan) {
        // 调用Service层进行修改,并返回修改结果
        return this.courseService.updateTeachplan(teachplan);
    }

    /**
     * 删除课程计划
     *
     * @param teachplanNode
     * @return
     */
    @Override
    @DeleteMapping("/teachplan/delete")
    public ResponseResult deleteTeachPlan(@RequestBody TeachplanNode teachplanNode) {
        // 调用Service层进行删除,并返回删除结果
        return this.courseService.deleteTeachPlan(teachplanNode);
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
    @GetMapping("/coursebase/get/{courseId}")
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

    /**
     * 添加课程图片
     *
     * @param courseId
     * @param pic
     * @return
     */
    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId, @RequestParam("pic") String pic) {
        // 调用service层进行添加
        return this.courseService.addCoursePic(courseId, pic);
    }

    /**
     * 查询课程图片
     *
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/coursepic/list/{courseId}")
    public CoursePic findCoursePic(@PathVariable("courseId") String courseId) {
        // 调用service层进行查询
        return this.courseService.findCoursePic(courseId);
    }

    /**
     * 删除课程图片
     *
     * @param courseId
     * @return
     */
    @Override
    @DeleteMapping("/coursepic/delete/{courseId}")
    public ResponseResult deleteCoursePic(@PathVariable("courseId") String courseId) {
        // 调用service层进行删除
        return this.courseService.deleteCoursePic(courseId);
    }

    /**
     * 课程数据模型查询
     *
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/courseview/{courseId}")
    public CourseView getCourseView(@PathVariable("courseId") String courseId) {
        // 调用service层进行查询
        return this.courseService.getCourseView(courseId);
    }

    /**
     * 保存课程计划关联的视频
     *
     * @param teachplanMedia
     * @return
     */
    @Override
    @PostMapping("/saveTeachplanMedia")
    public ResponseResult saveTeachplanMedia(@RequestBody TeachplanMedia teachplanMedia) {
        // 调用service层进行保存，并返回保存结果
        return this.courseService.saveTeachplanMedia(teachplanMedia);
    }
}