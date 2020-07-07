package com.xuecheng.media.service;

import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.model.domain.media.request.QueryMediaFileRequest;

/**
 * @author: HuangMuChen
 * @date: 2020/7/7 11:16
 * @version: V1.0
 * @Description: 媒资文件业务层接口
 */
public interface IMediaFileService {

    /**
     * 分页查询媒资文件列表
     *
     * @param page
     * @param size
     * @param queryMediaFileRequest
     * @return
     */
    QueryResponseResult findMediaFileList(int page, int size, QueryMediaFileRequest queryMediaFileRequest);
}