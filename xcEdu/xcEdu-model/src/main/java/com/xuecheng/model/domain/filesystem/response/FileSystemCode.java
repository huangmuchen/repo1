package com.xuecheng.model.domain.filesystem.response;

import com.google.common.collect.ImmutableMap;
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
public enum FileSystemCode implements ResultCode {
    // 枚举类型的实例对象建议全大写,且必须在最前面先定义，且必须按顺序维护枚举自定义的成员变量
    FS_UPLOADFILE_FILEISNULL(false, 25001, "上传文件为空！"),
    FS_UPLOADFILE_BUSINESSISNULL(false, 25002, "业务Id为空！"),
    FS_UPLOADFILE_SERVERFAIL(false, 25003, "上传文件服务器失败！"),
    FS_DELETEFILE_NOTEXISTS(false, 25004, "删除的文件不存在！"),
    FS_DELETEFILE_DBFAIL(false, 25005, "删除文件信息失败！"),
    FS_DELETEFILE_SERVERFAIL(false, 25006, "删除文件失败！"),
    FS_UPLOADFILE_METAERROR(false, 25007, "上传文件的元信息请使用json格式！"),
    FS_UPLOADFILE_USERISNULL(false, 25008, "上传文件用户为空！");

    // 操作是否成功
    @ApiModelProperty(value = "操作是否成功", example = "true", required = true)
    boolean success;
    // 操作代码
    @ApiModelProperty(value = "操作代码", example = "22001", required = true)
    int code;
    // 提示信息
    @ApiModelProperty(value = "操作提示", example = "操作过于频繁！", required = true)
    String message;

    private static final ImmutableMap<Integer, FileSystemCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, FileSystemCode> builder = ImmutableMap.builder();
        for (FileSystemCode commonCode : values()) {
            builder.put(commonCode.code(), commonCode);
        }
        CACHE = builder.build();
    }

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