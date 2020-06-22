package com.xuecheng.model.domain.system;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 字典数据项目实体类
 */
@Data
@ToString
public class SysDictionaryValue {
    @Field("sd_id")
    private String sdId; // 项目id
    @Field("sd_name")
    private String sdName; // 项目名称
    @Field("sd_status")
    private String sdStatus; // 项目状态（1：可用，0不可用）
}