package com.xuecheng.media_processor.service;

/**
 * @author: HuangMuChen
 * @date: 2020/7/5 0:39
 * @version: V1.0
 * @Description: 媒资视频处理业务层接口
 */
public interface IMediaProcessService {

    /**
     * 根据mediaId处理视频
     *
     * @param mediaId
     */
    void processMedia(String mediaId);
}