package com.xuecheng.model.domain.cms.ext;

import com.xuecheng.model.domain.cms.CmsTemplate;
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
public class CmsTemplateExt extends CmsTemplate {
    private String templateValue; // 模版内容
}