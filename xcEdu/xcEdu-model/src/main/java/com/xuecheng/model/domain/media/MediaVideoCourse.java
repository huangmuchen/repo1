package com.xuecheng.model.domain.media;

import com.xuecheng.utils.MD5Util;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
@Document(collection = "media_video_course")
public class MediaVideoCourse {
    @Id
    private String id;
    private String courseid; // 课程id
    private String chapter; // 章节id
    private String fileId; // 文件id
    private String processType; // 视频处理方式
    private String processStatus; // 视频处理状态
    private String hls_m3u8; // HLS处理结果
    private List<String> hls_ts_list;

    public MediaVideoCourse(String fileId, String courseid, String chapter) {
        this.fileId = fileId;
        this.courseid = courseid;
        this.chapter = chapter;
        this.id = MD5Util.getStringMD5(courseid + chapter);
        this.processType = "302002"; // 生成 hls
        this.processStatus = "303001"; // 未处理
    }
}