package com.xuecheng.model.domain.cms.response;

import com.xuecheng.common.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@ToString
@AllArgsConstructor
public enum CmsCode implements ResultCode {
    // 枚举类型的实例对象建议全大写,且必须在最前面先定义，且必须按顺序维护枚举自定义的成员变量
    CMS_ADDPAGE_EXISTSNAME(false, 24001, "页面名称已存在！"),
    CMS_GENERATEHTML_DATAURLISNULL(false, 24002, "从页面信息中找不到获取数据的url！"),
    CMS_GENERATEHTML_DATAISNULL(false, 24003, "根据页面的数据url获取不到数据！"),
    CMS_GENERATEHTML_TEMPLATEISNULL(false, 24004, "页面模板为空！"),
    CMS_GENERATEHTML_HTMLISNULL(false, 24005, "生成的静态html为空！"),
    CMS_GENERATEHTML_SAVEHTMLERROR(false, 24005, "保存静态html出错！"),
    CMS_COURSE_PERVIEWISNULL(false, 24007, "预览页面为空！");

    // 操作是否成功
    @ApiModelProperty(value = "操作是否成功", example = "true", required = true)
    boolean success;

    // 操作代码
    @ApiModelProperty(value = "操作代码", example = "22001", required = true)
    int code;

    // 提示信息
    @ApiModelProperty(value = "操作提示", example = "操作过于频繁！", required = true)
    String message;

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}