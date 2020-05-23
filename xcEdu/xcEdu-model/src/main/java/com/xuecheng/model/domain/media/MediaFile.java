package com.xuecheng.model.domain.media;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
@Document(collection = "media_file")
public class MediaFile {
    @Id
    private String fileId; // 文件id
    private String fileName; // 文件名称
    private String fileOriginalName; // 文件原始名称
    private String filePath; // 文件路径
    private String fileUrl; // 文件url
    private String fileType; // 文件类型
    private String mimeType; // mimetype
    private Long fileSize; // 文件大小
    private String fileStatus; // 文件状态
    private Date uploadTime; // 上传时间
    private String processStatus; // 处理状态
    private MediaFileProcess_m3u8 mediaFileProcess_m3u8; // hls处理
    private String tag; // tag标签用于查询
}