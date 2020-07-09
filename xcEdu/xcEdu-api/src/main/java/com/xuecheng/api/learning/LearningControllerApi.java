package com.xuecheng.api.learning;

import com.xuecheng.model.domain.learning.response.GetMediaResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: HuangMuChen
 * @date: 2020/7/9 21:18
 * @version: V1.0
 * @Description: Learning相关的对外暴露的接口：在Learning服务工程编写Controller类实现此接口
 */
@Api(value = "录播课程学习管理", description = "课录播课程学习管理接口，提供课程学习视频地址查询", tags = "CourseLearningApi")
public interface LearningControllerApi {

    /**
     * 查询录播课程学习地址
     *
     * @param courseId
     * @param teachplanId
     * @return
     */
    @ApiOperation("查询录播课程学习地址")
    GetMediaResult getMedia(String courseId, String teachplanId);
}