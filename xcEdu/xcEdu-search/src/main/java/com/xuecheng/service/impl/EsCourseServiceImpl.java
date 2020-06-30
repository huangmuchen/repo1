package com.xuecheng.service.impl;

import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.QueryResult;
import com.xuecheng.config.CourseIndexProperties;
import com.xuecheng.config.MediaIndexProperties;
import com.xuecheng.model.domain.course.CoursePub;
import com.xuecheng.model.domain.search.CourseSearchParam;
import com.xuecheng.service.IEsCourseService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author: HuangMuChen
 * @date: 2020/6/30 22:27
 * @version: V1.0
 * @Description: 课程搜索业务层实现类
 */
@Service
@Slf4j
@EnableConfigurationProperties({CourseIndexProperties.class, MediaIndexProperties.class})
public class EsCourseServiceImpl implements IEsCourseService {
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private CourseIndexProperties courseProp;
    @Autowired
    private MediaIndexProperties mediaProp;

    /**
     * 分页搜索课程：
     * 1、根据关键字搜索，采用MultiMatchQuery，搜索name、description、teachplan
     * 2、根据分类、课程等级搜索采用过虑器实现。
     * 3、分页查询。
     * 4、高亮显示。
     *
     * @param page
     * @param size
     * @param courseSearchParam
     * @return
     */
    @Override
    public QueryResponseResult<CoursePub> esList(int page, int size, CourseSearchParam courseSearchParam) {
        try {
            // 创建搜索请求对象
            SearchRequest searchRequest = new SearchRequest(courseProp.getIndex());
            // 指定类型
            searchRequest.types(courseProp.getType());
            // 创建搜索源构建对象
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            // TODO：设置查询条件
            // 向搜索请求对象中设置搜索源
            searchRequest.source(searchSourceBuilder);
            // 执行搜索,向ES发起http请求
            SearchResponse searchResponse = client.search(searchRequest);
            // 匹配到的总记录数
            long totalHits = searchResponse.getHits().getTotalHits();
            // TODO：遍历查询结果，进行数据封装
            QueryResult<CoursePub> queryResult = new QueryResult<>();
            // queryResult.setList();
            queryResult.setTotal(totalHits);
            return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
        } catch (IOException e) {
            log.error("ElasticSearch搜索异常，异常信息：{}", e.getMessage());
            return new QueryResponseResult<>(CommonCode.FAIL, new QueryResult<>());
        }
    }
}