package com.xuecheng.model.domain.cms.response;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: cms页面发布响应模型
 */
@Data
@NoArgsConstructor // feignClient返回值为复杂对象时其类型必须有无参构造函数
public class CmsPublishPageResult extends ResponseResult {
    private String pageUrl; // 页面发布成功cms返回页面的url=cmsSite.siteDomain+cmsSite.siteWebPath+ cmsPage.pageWebPath + cmsPage.pageName

    public CmsPublishPageResult(ResultCode resultCode, String pageUrl) {
        super(resultCode);
        this.pageUrl = pageUrl;
    }
}