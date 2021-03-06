package com.xuecheng.cms.dao;

import com.xuecheng.model.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author: HuangMuChen
 * @date: 2020/6/6 20:44
 * @version: V1.0
 * @Description: 使用Spring Data Mongodb完成Mongodb数据库的查询,同Spring Data JPA一样Spring Data Mongodb也提供自定义方法的规则
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> { // 指定实体类型和主键类型
    CmsTemplate findByAndTemplateFileId(String templateFileId);
}