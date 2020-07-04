package com.xuecheng.media.service;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.media.response.CheckChunkResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: HuangMuChen
 * @date: 2020/7/3 15:56
 * @version: V1.0
 * @Description: 媒资文件上传业务层接口
 */
public interface IMediaUploadService {

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
    ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt);

    /**
     * 检查文件块是否存在
     *
     * @param fileMd5
     * @param chunk
     * @param chunkSize
     * @return
     */
    CheckChunkResult checkchunk(String fileMd5, Integer chunk, Integer chunkSize);

    /**
     * 上传分块
     *
     * @param file
     * @param chunk
     * @param fileMd5
     * @return
     */
    ResponseResult uploadchunk(MultipartFile file, Integer chunk, String fileMd5);

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
    ResponseResult mergechunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt);
}