package com.xuecheng.model.domain.filesystem;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
@Document(collection = "filesystem")
public class FileSystem {
    @Id
    private String fileId; // 文件id
    private String filePath; // 文件请求路径
    private long fileSize; // 文件大小
    private String fileName; // 文件名称
    private String fileType; // 文件类型
    private int fileWidth; // 图片宽度
    private int fileHeight; // 图片高度
    private String userId; // 用户id，用于授权
    private String businesskey; // 业务key
    private String filetag; // 业务标签
    private Map metadata; // 文件元信息
}