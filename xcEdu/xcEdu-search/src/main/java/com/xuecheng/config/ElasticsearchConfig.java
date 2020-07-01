package com.xuecheng.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: HuangMuChen
 * @date: 2020/5/24 0:18
 * @version: V1.0
 * @Description: 配置类
 */
@Configuration
@EnableConfigurationProperties(SearchProperties.class)
public class ElasticsearchConfig {
    @Autowired
    private SearchProperties prop;

    /**
     * 项目主要使用RestHighLevelClient
     *
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        // 创建RestHighLevelClient客户端
        return new RestHighLevelClient(RestClient.builder(getHttpHostArray()));
    }

    /**
     * 项目中对于低级的客户端暂时不用
     *
     * @return
     */
    @Bean
    public RestClient restClient() {
        // 创建RestClient客户端
        return RestClient.builder(getHttpHostArray()).build();
    }

    /**
     * 获取httphost数组
     *
     * @return
     */
    public HttpHost[] getHttpHostArray() {
        // 解析hostlist配置信息
        String[] split = prop.getHostList().split(",");
        // 创建HttpHost数组，其中存放es主机和端口的配置信息
        HttpHost[] httpHostArray = new HttpHost[split.length];
        // 遍历数组，取出ip和端口
        for (int i = 0; i < split.length; i++) {
            String item = split[i];
            httpHostArray[i] = new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":")[1]), "http");
        }
        return httpHostArray;
    }
}