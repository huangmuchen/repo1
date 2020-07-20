package com.xuecheng.controller;

import com.xuecheng.api.search.EsCourseControllerApi;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.model.domain.course.CoursePub;
import com.xuecheng.model.domain.course.TeachplanMediaPub;
import com.xuecheng.model.domain.search.CourseSearchParam;
import com.xuecheng.service.IEsCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author: HuangMuChen
 * @date: 2020/6/30 22:24
 * @version: V1.0
 * @Description: 课程搜索控制层
 */
@RestController
@RequestMapping("/search/course")
public class EsCourseController implements EsCourseControllerApi {
    @Autowired
    private IEsCourseService esCourseService;

    /**
     * 分页搜索课程
     *
     * @param page
     * @param size
     * @param courseSearchParam
     * @return
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult<CoursePub> esList(@PathVariable("page") int page, @PathVariable("size") int size, CourseSearchParam courseSearchParam) {
        // 调用Service层进行搜索，并返回搜索结果
        return this.esCourseService.esList(page, size, courseSearchParam);
    }

    /**
     * 根据课程ID，查询课程信息
     *
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/getDetail/{id}")
    public CoursePub getDetail(@PathVariable("id") String courseId) {
        // 调用Service层进行搜索，并返回搜索结果
        return this.esCourseService.getDetail(courseId);
    }

    /**
     * 根据课程计划ID，查询播放地址
     *
     * @param teachplanId
     * @return
     */
    @Override
    @GetMapping("/getMedia/{teachplanId}")
    public TeachplanMediaPub getMedia(@PathVariable("teachplanId") String teachplanId) {
        // 调用Service层进行搜索，并返回搜索结果
        return this.esCourseService.getMedia(teachplanId);
    }

    /**
     * 根据课程ids，查询课程信息集合
     *
     * @param ids
     * @return
     */
    @GetMapping("/getBase/{ids}")
    public Map getBase(@PathVariable("ids") String ids) {
        // 取出课程ids
        List<String> courseIds = Arrays.asList(ids.split(","));
        // 调用Service层进行搜索，并返回搜索结果
        return this.esCourseService.getBase(courseIds);
    }
}