package com.xuecheng.model.domain.course.response;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 课程预览/发布响应模型
 */
@Data
@NoArgsConstructor
public class CoursePublishResult extends ResponseResult {
    String previewUrl; // 课程预览url

    public CoursePublishResult(ResultCode resultCode, String previewUrl){
        super(resultCode);
        this.previewUrl = previewUrl;
    }
}