package com.xuecheng.model.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 数据模型
 */
@Data
@ToString
@Document(collection = "cms_config")
public class CmsConfig {
    @Id // 主键
    private String id;
    private String name; // 数据模型的名称
    private List<CmsConfigModel> model; // 数据模型项目
}