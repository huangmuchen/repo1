package com.xuecheng.common.constant;

/**
 * @author: HuangMuChen
 * @date: 2020/7/4 21:21
 * @version: V1.0
 * @Description: 媒资文件相关常量
 */
public interface MediaConstant {
    // 媒资文件状态
    String MEDIAFILE_STATUS_SUCCESS = "301002"; // 上传成功
    String MEDIAFILE_STATUS_FAIL = "301003"; // 上传失败
    String MEDIAFILE_STATUS_NOTUPLOADED = "301001"; // 未上传
    // 媒资视频处理状态
    String MEDIAPROCESS_STATUS_SUCCESS = "303002"; // 处理成功
    String MEDIAPROCESS_STATUS_FAIL = "303003"; // 处理成功
    String MEDIAPROCESS_STATUS_PROCESSING = "303001"; // 处理中
}