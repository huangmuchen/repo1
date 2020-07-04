package com.xuecheng.api.media;

import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.media.response.CheckChunkResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: HuangMuChen
 * @date: 2020/7/3 16:26
 * @version: V1.0
 * @Description: 媒资文件上传对外暴露的接口：在Media服务工程编写Controller类实现此接口
 */
@Api(value = "媒资文件管理接口", description = "媒资文件管理接口，提供媒资文件的的查询、上传、删除和视频处理", tags = "MediaUploadApi")
public interface MediaUploadControllerApi {

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
    @ApiOperation("文件上传注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileMd5", value = "文件唯一标识", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "fileSize", value = "文件大小", required = true, paramType = "form", dataType = "long"),
            @ApiImplicitParam(name = "mimetype", value = "文件类型", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "fileExt", value = "文件扩展名", required = true, paramType = "form", dataType = "String")
    })
    ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt);

    /**
     * 检查文件块是否存在
     *
     * @param fileMd5
     * @param chunk
     * @param chunkSize
     * @return
     */
    @ApiOperation("文件块检查接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileMd5", value = "文件唯一标识", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "chunk", value = "当前分块下标", required = true, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "chunkSize", value = "当前分块大小", required = true, paramType = "form", dataType = "int")
    })
    CheckChunkResult checkchunk(String fileMd5, Integer chunk, Integer chunkSize);

    /**
     * 上传分块
     *
     * @param file
     * @param chunk
     * @param fileMd5
     * @return
     */
    @ApiOperation("文件上传接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传文件", required = true, paramType = "form", dataType = "file"),
            @ApiImplicitParam(name = "chunk", value = "第几个文件块", required = true, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "fileMd5", value = "文件唯一标识", required = true, paramType = "form", dataType = "String"),
    })
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
    @ApiOperation("文件合并接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileMd5", value = "文件唯一标识", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "fileSize", value = "文件大小", required = true, paramType = "form", dataType = "long"),
            @ApiImplicitParam(name = "mimetype", value = "文件类型", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "fileExt", value = "文件扩展名", required = true, paramType = "form", dataType = "String")
    })
    ResponseResult mergechunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt);
}