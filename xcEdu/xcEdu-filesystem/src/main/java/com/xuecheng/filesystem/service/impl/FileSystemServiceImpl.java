package com.xuecheng.filesystem.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xuecheng.common.exception.CustomException;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.filesystem.config.UploadProperties;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import com.xuecheng.filesystem.service.IFileSystemService;
import com.xuecheng.model.domain.filesystem.FileSystem;
import com.xuecheng.model.domain.filesystem.response.FileSystemCode;
import com.xuecheng.model.domain.filesystem.response.UploadFileResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * @author: HuangMuChen
 * @date: 2020/6/23 11:31
 * @version: V1.0
 * @Description: 文件系统业务层实现类
 */
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
@Service
public class FileSystemServiceImpl implements IFileSystemService {
    @Autowired
    private FileSystemRepository fileSystemRepository;
    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private UploadProperties prop;

    /**
     * 文件上传
     *
     * @param file
     * @param filetag
     * @param businesskey
     * @param metadata
     * @return
     */
    @Override
    public UploadFileResult upload(MultipartFile file, String filetag, String businesskey, String metadata) {
        // 校验file
        if (file == null) {
            throw new CustomException(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        // 上传文件到FDFS
        String fileId = uploadFileToFdfs(file);
        // 创建文件信息对象
        FileSystem fs = new FileSystem();
        // 封装数据
        fs.setFileId(fileId);
        fs.setFilePath(fileId); // 文件在文件系统中的路径
        fs.setFiletag(filetag);
        fs.setBusinesskey(businesskey);
        fs.setFileName(file.getOriginalFilename());
        fs.setFileType(file.getContentType());
        fs.setFileSize(file.getSize());
        // metadata为JSON格式的字符串，需转换为对象
        if (StringUtils.isNotEmpty(metadata)){
            Map map = JSON.parseObject(metadata, Map.class);
            fs.setMetadata(map);
        }
        // 保存文件信息到mogodb数据库中
        FileSystem fileSystem = this.fileSystemRepository.save(fs);
        // 返回上传结果
        return new UploadFileResult(CommonCode.SUCCESS, fileSystem);
    }

    /**
     * 上传文件到FDFS
     *
     * @param file
     * @return
     */
    public String uploadFileToFdfs(MultipartFile file) {
        try {
            // 获取文件名：123.jpg
            String fileName = file.getOriginalFilename();
            // 获取文件后缀名：jpg
            String fileExtName = StringUtils.substringAfterLast(fileName, ".");
            // 校验文件类型
            String contentType = file.getContentType();
            if (!prop.getSuffixes().contains(contentType)) {
                log.info("文件类型不合法：{}", fileName);
                throw new CustomException(FileSystemCode.FS_INVALID_FILE_TYPE);
            }
            // 校验文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                log.info("文件内容不合法：{}", fileName);
                throw new CustomException(FileSystemCode.FS_ILLEGAL_FILE_CONTENT);
            }
            // 上传文件到FastDFS
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), fileExtName, null);
            return storePath.getFullPath();
        } catch (IOException e) {
            log.error("上传文件失败!", e);
            throw new CustomException(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }
    }
}