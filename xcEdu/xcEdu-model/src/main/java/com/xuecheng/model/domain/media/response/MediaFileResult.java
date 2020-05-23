package com.xuecheng.model.domain.media.response;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
import com.xuecheng.model.domain.media.MediaFile;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@NoArgsConstructor
public class MediaFileResult extends ResponseResult {
    MediaFile mediaFile;

    public MediaFileResult(ResultCode resultCode, MediaFile mediaFile) {
        super(resultCode);
        this.mediaFile = mediaFile;
    }
}