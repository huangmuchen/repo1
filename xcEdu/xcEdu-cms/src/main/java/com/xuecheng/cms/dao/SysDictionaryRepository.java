package com.xuecheng.cms.dao;

import com.xuecheng.model.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author: HuangMuChen
 * @date: 2020/6/22 13:29
 * @version: V1.0
 * @Description: 使用Spring Data Mongodb完成Mongodb数据库的查询,同Spring Data JPA一样Spring Data Mongodb也提供自定义方法的规则
 */
public interface SysDictionaryRepository extends MongoRepository<SysDictionary, String> { // 指定实体类型和主键类型

    /**
     * 根据dType获取数据字典
     *
     * @param dType
     * @return
     */
    SysDictionary findByDType(String dType);
}