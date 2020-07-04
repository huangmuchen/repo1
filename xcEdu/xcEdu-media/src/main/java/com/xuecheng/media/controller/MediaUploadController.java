package com.xuecheng.media.controller;

import com.xuecheng.api.media.MediaUploadControllerApi;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.media.service.IMediaUploadService;
import com.xuecheng.model.domain.media.response.CheckChunkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: HuangMuChen
 * @date: 2020/7/3 16:24
 * @version: V1.0
 * @Description: 媒资文件上传控制层
 */
@RestController
@RequestMapping("/media/upload")
public class MediaUploadController implements MediaUploadControllerApi {
    @Autowired
    private IMediaUploadService mediaUploadService;

    /**
     * 文件上传前进行一系列文件检查及文件夹创建
     *
     * @param fileMd5
     * @param fileName
     * @param fileSize
     * @param mimetype
     * @param fileExt
     * @return
     */
    @Override
    @PostMapping("/register")
    public ResponseResult register(@RequestParam("fileMd5") String fileMd5,
                                   @RequestParam("fileName") String fileName,
                                   @RequestParam("fileSize") Long fileSize,
                                   @RequestParam("mimetype") String mimetype,
                                   @RequestParam("fileExt") String fileExt) {
        // 调用service层进行分块上传前注册
        return this.mediaUploadService.register(fileMd5, fileName, fileSize, mimetype, fileExt);
    }

    /**
     * 检查文件块是否存在
     *
     * @param fileMd5
     * @param chunk
     * @param chunkSize
     * @return
     */
    @PostMapping("/checkchunk")
    @Override
    public CheckChunkResult checkchunk(@RequestParam("fileMd5") String fileMd5, @RequestParam("chunk") Integer chunk, @RequestParam("chunkSize") Integer chunkSize) {
        // 调用service层进行分块上传前分块检查
        return this.mediaUploadService.checkchunk(fileMd5, chunk, chunkSize);
    }

    /**
     * 上传分块
     *
     * @param file    文件块
     * @param chunk   第几个文件块
     * @param fileMd5 文件唯一标识
     * @return
     */
    @Override
    @PostMapping("/uploadchunk")
    public ResponseResult uploadchunk(@RequestParam("file") MultipartFile file, @RequestParam("chunk") Integer chunk, @RequestParam("fileMd5") String fileMd5) {
        // 调用service层进行分块上传
        return this.mediaUploadService.uploadchunk(file, chunk, fileMd5);
    }

    /**
     * 文件块全部上传完毕后，进行合并，并删除之前的文件块
     *
     * @param fileMd5
     * @param fileName
     * @param fileSize
     * @param mimetype
     * @param fileExt
     * @return
     */
    @PostMapping("/mergechunks")
    @Override
    public ResponseResult mergechunks(@RequestParam("fileMd5") String fileMd5,
                                      @RequestParam("fileName") String fileName,
                                      @RequestParam("fileSize") Long fileSize,
                                      @RequestParam("mimetype") String mimetype,
                                      @RequestParam("fileExt") String fileExt) {
        // 调用service层进行分块合并
        return this.mediaUploadService.mergechunks(fileMd5, fileName, fileSize, mimetype, fileExt);
    }
}