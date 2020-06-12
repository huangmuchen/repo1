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
    CMS_ADD_PAGE_EXISTS(false, 24001, "页面已存在！"),
    CMS_GENERATEHTML_DATAURL_ISNULL(false, 24002, "从页面信息中找不到获取数据的url！"),
    CMS_GENERATEHTML_DATA_ISNULL(false, 24003, "根据页面的数据url获取不到数据！"),
    CMS_GENERATEHTML_TEMPLATEFILE_ISNULL(false, 24004, "页面模板文件为空！"),
    CMS_GENERATEHTML_HTML_ISNULL(false, 24005, "生成的静态html为空！"),
    CMS_GENERATEHTML_SAVEHTML_ERROR(false, 24006, "保存静态html出错！"),
    CMS_COURSE_PERVIEW_ISNULL(false, 24007, "预览页面为空！"),
    CMS_ADD_PAGEDATA_ISNULL(false,24008,"新增页面数据为空！"),
    CMS_FIND_PAGE_NOTEXIST(false,24009,"页面不存在！"),
    CMS_SAVE_PAGE_ERROR(false,24010,"服务端保存页面失败！"),
    CMS_UPLOAD_TEMPLATEFILE_ISNULL(false,25001,"上传文件为空！"),
    CMS_DELETE_TEMPLATEFILE_ISNULL(false,25002,"删除的模板文件不存在！"),
    CMS_ADD_TEMPLATEDATA_ISNULL(false,25003,"新增模板数据为空！"),
    CMS_DELETE_TEMPLATE_ISFALL(false,25004,"模板ID有误，删除失败！"),
    CMS_TEMPLATE_HAVEPAGE (false,25005,"此模板下包含页面，删除失败！") ,
    CMS_SAVE_TEMPLATEFILE_ERROR(false,25008,"服务端保存模板文件失败！"),
    CMS_SAVE_TEMPLATE_ERROR(false,25008,"服务端保存模板失败！"),
    CMS_FIND_TEMPLATE_NOTEXIST(false,24009,"模板不存在！");

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