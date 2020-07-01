package com.xuecheng.service.impl;

import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.QueryResult;
import com.xuecheng.config.SearchProperties;
import com.xuecheng.model.domain.course.CoursePub;
import com.xuecheng.model.domain.search.CourseSearchParam;
import com.xuecheng.service.IEsCourseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: HuangMuChen
 * @date: 2020/6/30 22:27
 * @version: V1.0
 * @Description: 课程搜索业务层实现类
 */
@Service
@Slf4j
@EnableConfigurationProperties(SearchProperties.class)
public class EsCourseServiceImpl implements IEsCourseService {
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private SearchProperties prop;

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
            // 创建搜索请求对象，指定索引
            SearchRequest searchRequest = new SearchRequest(prop.getCourseIndex());
            // 指定类型
            searchRequest.types(prop.getCourseType());
            // 创建搜索源构建对象
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            // 设置源字段过虑,第一个参数结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
            String[] sourceFields = prop.getCourseSourceFields().split(",");
            searchSourceBuilder.fetchSource(sourceFields, new String[]{});
            // 定义一个BoolQuery
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            // 根据关键字进行全文检索
            if (StringUtils.isNotBlank(courseSearchParam.getKeyword())) {
                // 定义一个MultiMatchQuery，匹配关键字，搜索字段，匹配占比，提升权值
                MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(courseSearchParam.getKeyword(), "name", "description", "teachplan").minimumShouldMatch("70%").field("name", 10);
                boolQueryBuilder.must(multiMatchQueryBuilder);
            }
            // 根据一级分类进行精确搜索
            if (StringUtils.isNotBlank(courseSearchParam.getMt())) {
                // 设置过滤条件
                boolQueryBuilder.filter(QueryBuilders.termQuery("mt", courseSearchParam.getMt()));
            }
            // 根据二级分类进行精确搜索
            if (StringUtils.isNotBlank(courseSearchParam.getSt())) {
                // 设置过滤条件
                boolQueryBuilder.filter(QueryBuilders.termQuery("st", courseSearchParam.getSt()));
            }
            // 根据课程等级进行精确搜索
            if (StringUtils.isNotBlank(courseSearchParam.getGrade())) {
                // 设置过滤条件
                boolQueryBuilder.filter(QueryBuilders.termQuery("grade", courseSearchParam.getGrade()));
            }
            // 根据价格区间进行搜索
            Float price_min = courseSearchParam.getPrice_min();
            Float price_max = courseSearchParam.getPrice_max();
            if (price_min != null && price_max != null && price_min <= price_max) {
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(price_min).lte(price_max));
            }
            // 设置es中搜索时起始下标，默认为0
            int start = (page < 1 ? 0 : page - 1) * (size < 0 ? 5 : size);
            // 设置分页参数
            searchSourceBuilder.from(start);
            searchSourceBuilder.size(size < 0 ? 5 : size);
            // 进行布尔查询
            searchSourceBuilder.query(boolQueryBuilder);
            // 添加排序
            if (StringUtils.isNotBlank(courseSearchParam.getSort())) {
                searchSourceBuilder.sort(courseSearchParam.getSort(), SortOrder.DESC);
            }
            // 创建高亮构建对象：
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            // 设置高亮标签前缀
            highlightBuilder.preTags("<font class='eslight'>");
            // 设置高亮标签后缀
            highlightBuilder.postTags("</font>");
            // 设置高亮字段
            highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
            // 在搜索中应用高亮设置
            searchSourceBuilder.highlighter(highlightBuilder);
            // 向搜索请求对象中设置搜索源
            searchRequest.source(searchSourceBuilder);
            // 执行搜索,向ES发起http请求
            SearchResponse searchResponse = client.search(searchRequest);
            // 匹配到的总记录数
            long totalHits = searchResponse.getHits().getTotalHits();
            // 得到匹配度高的文档
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            // 创建集合列表
            List<CoursePub> list = new ArrayList<>();
            for (SearchHit hit : searchHits) {
                // 创建一个CoursePub对象
                CoursePub coursePub = new CoursePub();
                // 取出源文档内容
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                // 取出id
                String id = (String) sourceAsMap.get("id");
                coursePub.setId(id);
                // 取出名称
                String name = (String) sourceAsMap.get("name");
                // 取出高亮字段
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                if (highlightFields != null) {
                    // 取出name高亮字段
                    HighlightField nameField = highlightFields.get("name");
                    if (nameField != null) {
                        Text[] fragments = nameField.getFragments();
                        StringBuilder sb = new StringBuilder();
                        for (Text text : fragments) {
                            sb.append(text);
                        }
                        name = sb.toString();
                    }
                }
                coursePub.setName(name);
                // 取出图片
                String pic = (String) sourceAsMap.get("pic");
                coursePub.setPic(pic);
                // 取出价格
                if (sourceAsMap.get("price") != null) {
                    coursePub.setPrice((Double) sourceAsMap.get("price"));
                }
                // 取出原价
                if (sourceAsMap.get("price_old") != null) {
                    coursePub.setPrice_old((Double) sourceAsMap.get("price_old"));
                }
                list.add(coursePub);
            }
            // 创建QueryResult对象
            QueryResult<CoursePub> queryResult = new QueryResult<>();
            // 封装数据列表
            queryResult.setList(list);
            // 封装总记录
            queryResult.setTotal(totalHits);
            // 返回查询结果
            return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
        } catch (Exception e) {
            log.error("xuecheng search error！异常信息：{}", e.getMessage());
            return new QueryResponseResult<>(CommonCode.FAIL, new QueryResult<>());
        }
    }
}