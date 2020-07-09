package com.xuecheng.learning.controller;

import com.xuecheng.api.learning.LearningControllerApi;
import com.xuecheng.learning.service.ILearningService;
import com.xuecheng.model.domain.learning.response.GetMediaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: HuangMuChen
 * @date: 2020/7/9 21:22
 * @version: V1.0
 * @Description: 课程学习控制层
 */
@RestController
@RequestMapping("/learning/course")
public class LearningController implements LearningControllerApi {
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
    @GetMapping("/getMedia/{courseId}/{teachplanId}")
    public GetMediaResult getMedia(@PathVariable("courseId") String courseId, @PathVariable("teachplanId") String teachplanId) {
        // 调用service层进行查询，并返回查询结果
        return this.learningService.getMedia(courseId, teachplanId);
    }
}