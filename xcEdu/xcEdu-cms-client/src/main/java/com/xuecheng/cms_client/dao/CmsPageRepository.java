package com.xuecheng.cms_client.dao;

import com.xuecheng.model.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author: HuangMuChen
 * @date: 2020/5/28 13:49
 * @version: V1.0
 * @Description: 使用Spring Data Mongodb完成Mongodb数据库的查询,同Spring Data JPA一样Spring Data Mongodb也提供自定义方法的规则
 */
public interface CmsPageRepository extends MongoRepository<CmsPage, String> { // 指定实体类型和主键类型
}