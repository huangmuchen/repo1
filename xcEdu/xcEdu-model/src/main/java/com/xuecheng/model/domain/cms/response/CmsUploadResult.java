package com.xuecheng.model.domain.cms.response;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
import lombok.Data;

/**
 * @author: HuangMuChen
 * @date: 2020/6/12 10:21
 * @version: V1.0
 * @Description: cms文件上传响应模型
 */
@Data
public class CmsUploadResult extends ResponseResult {
    private String templateFileId; // 模板文件id

    public CmsUploadResult(ResultCode resultCode, String templateFileId) {
        super(resultCode);
        this.templateFileId = templateFileId;
    }
}