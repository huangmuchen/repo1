package com.xuecheng.model.domain.cms;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
public class CmsConfigModel {
    private String key;
    private String name;
    private String url;
    private Map mapValue;
    private String value;
}