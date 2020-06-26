package com.xuecheng.model.domain.cms.response;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
import com.xuecheng.model.domain.cms.CmsPage;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: cms页面添加/修改响应模型
 */
@Data
@NoArgsConstructor
public class CmsPageResult extends ResponseResult {
    private CmsPage cmsPage;

    public CmsPageResult(ResultCode resultCode, CmsPage cmsPage) {
        super(resultCode);
        this.cmsPage = cmsPage;
    }
}