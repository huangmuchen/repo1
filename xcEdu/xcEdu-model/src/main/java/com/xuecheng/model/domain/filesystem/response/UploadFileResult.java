package com.xuecheng.model.domain.filesystem.response;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
import com.xuecheng.model.domain.filesystem.FileSystem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
public class UploadFileResult extends ResponseResult {
    @ApiModelProperty(value = "文件信息", example = "true", required = true)
    FileSystem fileSystem;

    public UploadFileResult(ResultCode resultCode, FileSystem fileSystem) {
        super(resultCode);
        this.fileSystem = fileSystem;
    }
}