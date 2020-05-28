package com.xuecheng.cms.service.impl;

import com.xuecheng.cms.service.ICmsPageService;
import com.xuecheng.common.model.response.QueryResponseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: HuangMuChen
 * @date: 2020/5/28 18:16
 * @version: V1.0
 * @Description: 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsPageServiceImplTest {
    @Autowired
    private ICmsPageService cmsPageService;

    @Test
    public void testFindList() {
        QueryResponseResult result = this.cmsPageService.findList(1, 10, null);
        System.out.println(result);
    }
}