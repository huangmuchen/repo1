package com.xuecheng.model.domain.media.request;

import com.xuecheng.common.model.request.RequestData;
import lombok.Data;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 封装媒资文件请求条件
 */
@Data
public class QueryMediaFileRequest extends RequestData {
    private String fileOriginalName; // 文件原始名称
    private String processStatus; // 处理状态
    private String tag; // tag标签用于查询
}