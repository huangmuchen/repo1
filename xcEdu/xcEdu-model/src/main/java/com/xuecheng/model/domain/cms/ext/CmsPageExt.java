package com.xuecheng.model.domain.cms.ext;

import com.xuecheng.model.domain.cms.CmsPage;
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
public class CmsPageExt extends CmsPage {
    private String htmlValue;
}