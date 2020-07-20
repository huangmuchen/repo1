package com.xuecheng.learning.controller;

import com.xuecheng.api.learning.LearningControllerApi;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.web.BaseController;
import com.xuecheng.learning.service.ILearningService;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import com.xuecheng.model.domain.learning.response.GetMediaResult;
import com.xuecheng.utils.XcOauth2Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: HuangMuChen
 * @date: 2020/7/9 21:22
 * @version: V1.0
 * @Description: 课程学习控制层
 */
@RestController
@RequestMapping("/learning")
public class LearningController extends BaseController implements LearningControllerApi {
    @Autowired
    private ILearningService learningService;

    /**
     * 查询录播课程学习地址
     *
     * @param courseId
     * @param teachplanId
     * @return
     */
    @Override
    @GetMapping("/course/getMedia/{courseId}/{teachplanId}")
    public GetMediaResult getMedia(@PathVariable("courseId") String courseId, @PathVariable("teachplanId") String teachplanId) {
        // 调用service层进行查询，并返回查询结果
        return this.learningService.getMedia(courseId, teachplanId);
    }

    /**
     * 根据用户id查询用户已选课程
     *
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    @PostMapping("/choosecourse/list/{page}/{size}")
    @Override
    public QueryResponseResult chooseCourseList(@PathVariable("page") int page, @PathVariable("size") int size, CourseListRequest courseListRequest) {
        // 通过工具类获取Jwt中的用户信息
        XcOauth2Util.UserJwt userJwt = XcOauth2Util.getUserJwtFromHeader(request);
        // 取出用户id
        String userId = userJwt.getId();
        // 调用service层进行查询
        return this.learningService.chooseCourseList(page, size, userId, courseListRequest);
    }
}