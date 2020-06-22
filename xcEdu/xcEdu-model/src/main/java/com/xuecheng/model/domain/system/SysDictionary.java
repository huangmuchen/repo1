package com.xuecheng.model.domain.system;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 数据字典实体类
 */
@Data
@ToString
@Document(collection = "sys_dictionary")
public class SysDictionary {
    @Id
    private String id; // 字典id
    @Field("d_name")
    private String dName; // 字典名称
    @Field("d_type")
    private String dType; // 字典分类
    @Field("d_value")
    private List<SysDictionaryValue> dValue; // 字典数据
}