package com.xuecheng.filesystem.service;

import com.xuecheng.model.domain.filesystem.response.UploadFileResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: HuangMuChen
 * @date: 2020/6/23 11:30
 * @version: V1.0
 * @Description: 文件系统业务层接口
 */
public interface IFileSystemService {

    /**
     * 文件上传
     *
     * @param file
     * @param filetag
     * @param businesskey
     * @param metadata
     * @return
     */
    UploadFileResult upload(MultipartFile file, String filetag, String businesskey, String metadata);
}