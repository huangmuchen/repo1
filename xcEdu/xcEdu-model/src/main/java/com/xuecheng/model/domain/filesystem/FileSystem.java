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
 * @Description: 文件信息的模型类
 */
@Data
@ToString
@Document(collection = "filesystem")
public class FileSystem {
    @Id // 主键
    private String fileId; // fastDFS返回的文件id
    private String filePath; // 请求fastDFS浏览文件URL
    private long fileSize; // 文件大小
    private String fileName; // 文件名称
    private String fileType; // 文件类型
    private int fileWidth; // 图片宽度
    private int fileHeight; // 图片高度
    private String userId; // 用户id，用于授权
    private String businesskey; // 业务key
    private String filetag; // 业务标签，，由于文件系统服务是公共服务，文件系统服务会为使用文件系统服务的子系统分配文件标签，用于标识此文件来自哪个系统
    private Map metadata; // 文件元信息
}