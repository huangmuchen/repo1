package com.xuecheng.model.domain.cms.response;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
@NoArgsConstructor
public class CoursePreviewResult extends ResponseResult {
    String url;

    public CoursePreviewResult(ResultCode resultCode, String url) {
        super(resultCode);
        this.url = url;
    }
}