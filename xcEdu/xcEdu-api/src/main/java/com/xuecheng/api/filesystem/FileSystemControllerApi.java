package com.xuecheng.api.filesystem;

import com.xuecheng.model.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: HuangMuChen
 * @date: 2020/6/23 0:28
 * @version: V1.0
 * @Description: FileSystem(文件系统)相关的对外暴露的接口：在FileSystem服务工程编写Controller类实现此接口
 */
@Api(value = "文件管理系统", description = "文件管理的接口，提供文件的上传，下载，查询", tags = "FileSystemApi")
public interface FileSystemControllerApi {

    /**
     * 文件上传
     *
     * @param file
     * @param filetag
     * @param businesskey
     * @param metadata
     * @return
     */
    @ApiOperation("文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传文件", required = true, paramType = "form", dataType = "file"),
            @ApiImplicitParam(name = "filetag", value = "文件标签，标识文件来自哪个系统", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "businesskey", value = "业务标识", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "metadata", value = "文件元信息", required = false, paramType = "form", dataType = "String"),
    })
    UploadFileResult upload(MultipartFile file, String filetag, String businesskey, String metadata);
}