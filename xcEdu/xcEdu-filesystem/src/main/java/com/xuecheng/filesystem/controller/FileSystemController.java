package com.xuecheng.filesystem.controller;

import com.xuecheng.api.filesystem.FileSystemControllerApi;
import com.xuecheng.filesystem.service.IFileSystemService;
import com.xuecheng.model.domain.filesystem.response.UploadFileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: HuangMuChen
 * @date: 2020/6/23 11:32
 * @version: V1.0
 * @Description: 文件系统控制层
 */
@RestController
@RequestMapping("/filesystem")
public class FileSystemController implements FileSystemControllerApi {
    @Autowired
    private IFileSystemService fileSystemService;

    /**
     * 文件上传
     *
     * @param file
     * @param filetag
     * @param businesskey
     * @param metadata
     * @return
     */
    @PostMapping("/upload")
    @Override
    public UploadFileResult upload(@RequestParam("file") MultipartFile file,
                                   @RequestParam(value = "filetag") String filetag,
                                   @RequestParam(value = "businesskey", required = false) String businesskey,
                                   @RequestParam(value = "metadata", required = false) String metadata) {
        // 调用service层进行上传，并返回上传结果
        return this.fileSystemService.upload(file, filetag, businesskey, metadata);
    }
}