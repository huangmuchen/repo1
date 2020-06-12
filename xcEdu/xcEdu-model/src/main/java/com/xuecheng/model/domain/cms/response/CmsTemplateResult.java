package com.xuecheng.model.domain.cms.response;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
import com.xuecheng.model.domain.cms.CmsTemplate;
import lombok.Data;

/**
 * @author: HuangMuChen
 * @date: 2020/6/12 10:15
 * @version: V1.0
 * @Description: cms模板添加/修改响应模型
 */
@Data
public class CmsTemplateResult extends ResponseResult {
    private CmsTemplate cmsTemplate;

    public CmsTemplateResult(ResultCode resultCode, CmsTemplate cmsTemplate) {
        super(resultCode);
        this.cmsTemplate = cmsTemplate;
    }
}