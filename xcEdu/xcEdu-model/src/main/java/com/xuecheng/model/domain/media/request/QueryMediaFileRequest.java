package com.xuecheng.model.domain.media.request;

import com.xuecheng.common.model.request.RequestData;
import lombok.Data;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
public class QueryMediaFileRequest extends RequestData {
    private String fileOriginalName;
    private String processStatus;
    private String tag;
}