package com.xuecheng.cms.other;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author: HuangMuChen
 * @date: 2020/6/10 22:04
 * @version: V1.0
 * @Description: 测试RestTemplate
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RestTemplateTest {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 根据url获取数据，并转为Map格式
     */
    @Test
    public void testRestTemplate() {
        // 发起http请求
        ResponseEntity<Map> entity = this.restTemplate.getForEntity("http://localhost:31001/cms/config/getModel/5a791725dd573c3574ee333f", Map.class);
        // 打印响应体
        System.out.println(entity.getBody());
    }
}