package com.xuecheng.model.domain.cms;

import lombok.Data;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
public class CmsPageParam {
    private String pageParamName;  // 参数名称
    private String pageParamValue; // 参数值
}