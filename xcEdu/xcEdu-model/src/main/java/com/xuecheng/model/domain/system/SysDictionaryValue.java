package com.xuecheng.model.domain.system;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
public class SysDictionaryValue {
    @Field("sd_id")
    private String sdId;
    @Field("sd_name")
    private String sdName;
    @Field("sd_status")
    private String sdStatus;
}