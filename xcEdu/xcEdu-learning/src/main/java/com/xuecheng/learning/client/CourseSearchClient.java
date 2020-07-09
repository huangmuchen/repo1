package com.xuecheng.learning.client;

import com.xuecheng.common.client.XcServiceList;
import com.xuecheng.model.domain.course.TeachplanMediaPub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: HuangMuChen
 * @date: 2020/7/9 21:31
 * @version: V1.0
 * @Description: 通过Feign远程调用Search服务
 */
@FeignClient(value = XcServiceList.XCEDU_SEARCH)
public interface CourseSearchClient {

    /**
     * 根据课程计划ID，查询播放地址
     *
     * @param teachplanId
     * @return
     */
    @GetMapping("/search/course/getMedia/{teachplanId}")
    TeachplanMediaPub getMedia(@PathVariable("teachplanId") String teachplanId);
}