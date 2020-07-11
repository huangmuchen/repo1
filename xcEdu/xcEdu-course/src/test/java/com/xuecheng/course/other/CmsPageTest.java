package com.xuecheng.course.other;

import com.xuecheng.course.client.CmsPageClient;
import com.xuecheng.model.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @author: HuangMuChen
 * @date: 2020/6/25 17:55
 * @version: V1.0
 * @Description: 负载均衡测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageTest {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CmsPageClient cmsPageClient;

    /**
     * 测试ribbon负载均衡
     * 可在RibbonLoadBalancerClient.class的57行打断点进行调试
     */
    @Test
    public void testRibbon() {
        // 服务id
        String serviceId = "xcEdu-cms";
        // 远程调用cms服务
        ResponseEntity<CmsPage> entity = this.restTemplate.getForEntity("http://" + serviceId + "/cms/page/find/5e4fd031392c022514c5122e", CmsPage.class);
        // 打印查询结果
        System.out.println(entity.getBody());
    }

    /**
     * 测试Feign远程调用功能
     */
    @Test
    public void testFeign() {
        // 通过Feign远程调用CmsPage服务
        CmsPage cmsPage = this.cmsPageClient.findByPageId("5e4fd031392c022514c5122e");
        // 打印查询结果
        System.out.println(cmsPage);
    }
}