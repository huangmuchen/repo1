package com.xuecheng.cms_client.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: HuangMuChen
 * @date: 2020/5/24 0:18
 * @version: V1.0
 * @Description: MongoDB配置类
 */
@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.database}")
    private String db; // xc_cms

    /**
     * GridFSBucket用于打开下载流对象
     *
     * @param mongoClient
     * @return
     */
    @Bean
    public GridFSBucket getGridFSBucket(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(db);
        return GridFSBuckets.create(database);
    }
}