package com.xuecheng.media.controller;

import com.xuecheng.api.media.MediaFileControllerApi;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.media.service.IMediaFileService;
import com.xuecheng.model.domain.media.request.QueryMediaFileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: HuangMuChen
 * @date: 2020/7/7 11:14
 * @version: V1.0
 * @Description: 媒资文件控制层
 */
@RestController
@RequestMapping("/media/file")
public class MediaFileController implements MediaFileControllerApi {
    @Autowired
    private IMediaFileService mediaFileService;

    /**
     * 分页查询媒资文件列表
     *
     * @param page
     * @param size
     * @param queryMediaFileRequest
     * @return
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findMediaFileList(@PathVariable("page") int page, @PathVariable("size") int size, QueryMediaFileRequest queryMediaFileRequest) {
        // 调用Service层进行查询，并返回查询结果
        return this.mediaFileService.findMediaFileList(page, size, queryMediaFileRequest);
    }
}