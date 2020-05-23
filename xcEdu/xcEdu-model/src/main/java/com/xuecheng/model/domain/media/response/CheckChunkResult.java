package com.xuecheng.model.domain.media.response;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
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
public class CheckChunkResult extends ResponseResult {
    @ApiModelProperty(value = "文件分块存在标记", example = "true", required = true)
    boolean fileExist;

    public CheckChunkResult(ResultCode resultCode, boolean fileExist) {
        super(resultCode);
        this.fileExist = fileExist;
    }
}