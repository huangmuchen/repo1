package com.xuecheng.model.domain.cms.response;

import com.xuecheng.common.model.response.ResultCode;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: CMS响应状态码
 */
@ToString
@AllArgsConstructor
public enum CmsCode implements ResultCode {
    CMS_ADDPAGE_EXISTS(false, 24001, "页面已存在！"),
    CMS_GENERATEHTML_DATAURLISNULL(false, 24002, "从页面信息中找不到获取数据的url！"),
    CMS_GENERATEHTML_DATAISNULL(false, 24003, "根据页面的数据url获取不到数据！"),
    CMS_GENERATEHTML_TEMPLATEISNULL(false, 24004, "页面模板为空！"),
    CMS_GENERATEHTML_HTMLISNULL(false, 24005, "生成的静态html为空！"),
    CMS_GENERATEHTML_SAVEHTMLERROR(false, 24006, "保存静态html出错！"),
    CMS_COURSE_PERVIEWISNULL(false, 24007, "预览页面为空！"),
    CMS_ADDPAGE_PARAM_ERROR(false,24008,"参数异常！"),
    CMS_FINDPAGE_NOTEXIST(false,24009,"页面不存在！"),
    CMS_SAVEPAGE_ERROR(false,24010,"服务端保存页面失败！");

    // 操作是否成功
    boolean success;
    // 操作代码
    int code;
    // 提示信息
    String message;

    @Override
    public boolean success() {
        return this.success;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}