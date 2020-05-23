package com.xuecheng.model.domain.media.response;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.common.model.response.ResultCode;
import com.xuecheng.model.domain.media.MediaFile;
import com.xuecheng.model.domain.media.MediaVideoCourse;
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
public class MediaCourseResult extends ResponseResult {
    MediaFile mediaVideo;
    MediaVideoCourse mediaVideoCourse;

    public MediaCourseResult(ResultCode resultCode, MediaVideoCourse mediaVideoCourse) {
        super(resultCode);
        this.mediaVideoCourse = mediaVideoCourse;
    }
}