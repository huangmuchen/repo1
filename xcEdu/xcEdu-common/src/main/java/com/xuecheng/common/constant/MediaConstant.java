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
    String MEDIAPROCESS_STATUS_FAIL = "303003"; // 处理失败
    String MEDIAPROCESS_STATUS_UNPROCESS = "303001"; // 未处理(处理中)
    String MEDIAPROCESS_STATUS_PROCESSED = "303004"; // 无需处理
}