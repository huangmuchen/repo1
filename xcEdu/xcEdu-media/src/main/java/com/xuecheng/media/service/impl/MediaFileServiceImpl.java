package com.xuecheng.media.service.impl;

import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.QueryResult;
import com.xuecheng.media.dao.MediaFileRepository;
import com.xuecheng.media.service.IMediaFileService;
import com.xuecheng.model.domain.media.MediaFile;
import com.xuecheng.model.domain.media.request.QueryMediaFileRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author: HuangMuChen
 * @date: 2020/7/7 11:17
 * @version: V1.0
 * @Description: 媒资文件业务层实现类
 */
@Service
@Slf4j
public class MediaFileServiceImpl implements IMediaFileService {
    @Autowired
    private MediaFileRepository mediaFileRepository;

    /**
     * 分页查询媒资文件列表
     *
     * @param page
     * @param size
     * @param queryMediaFileRequest
     * @return
     */
    @Override
    public QueryResponseResult findMediaFileList(int page, int size, QueryMediaFileRequest queryMediaFileRequest) {
        try {
            // 初始化page
            if (page <= 0) {
                page = 1;
            }
            // 初始化size
            if (size <= 0) {
                size = 5;
            }
            // 初始化查询条件
            if (queryMediaFileRequest == null) {
                queryMediaFileRequest = new QueryMediaFileRequest();
            }
            // 条件匹配器，需要自定义字符串的匹配器实现模糊查询
            ExampleMatcher matcher = ExampleMatcher.matching()
                    // tag字段模糊匹配(%{tag}%，
                    .withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.contains())
                    // 文件原始名称模糊查询(%{fileOriginalName}%，
                    .withMatcher("fileOriginalName", ExampleMatcher.GenericPropertyMatchers.contains())
                    // 处理状态精确匹配（默认）
                    .withMatcher("processStatus", ExampleMatcher.GenericPropertyMatchers.exact());
            // 构建查询mediaFile
            MediaFile mediaFile = new MediaFile();
            // 校验并设置查询条件
            if (StringUtils.isNotBlank(queryMediaFileRequest.getTag())) {
                mediaFile.setTag(queryMediaFileRequest.getTag());
            }
            if (StringUtils.isNotBlank(queryMediaFileRequest.getFileOriginalName())) {
                mediaFile.setFileOriginalName(queryMediaFileRequest.getFileOriginalName());
            }
            if (StringUtils.isNotBlank(queryMediaFileRequest.getProcessStatus())) {
                mediaFile.setProcessStatus(queryMediaFileRequest.getProcessStatus());
            }
            // 构建条件实例
            Example<MediaFile> example = Example.of(mediaFile, matcher);
            // 封装分页对象,默认从0开始索引页(为了适应mongodb的接口将页码减1)
            PageRequest pageRequest = PageRequest.of(page - 1, size);
            // 自定义分页查询
            Page<MediaFile> all = this.mediaFileRepository.findAll(example, pageRequest);
            // 构建返回结果对象,并封装查询结果
            QueryResult<MediaFile> queryResult = new QueryResult<>();
            // 设置页面列表
            queryResult.setList(all.getContent());
            // 设置总条数
            queryResult.setTotal(all.getTotalElements());
            // 返回查询成功结果
            return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
        } catch (Exception e) {
            // 返回查询失败结果
            return new QueryResponseResult<MediaFile>(CommonCode.FAIL, new QueryResult<>());
        }
    }
}