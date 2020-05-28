package com.xuecheng.cms.dao;

import com.xuecheng.model.domain.cms.CmsPage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @author: HuangMuChen
 * @date: 2020/5/28 14:02
 * @version: V1.0
 * @Description: 单元测试
 */
@RunWith(SpringRunner.class)
// 启动测试类会从main下找springBoot启动类，加载spring容器：(classes = CmsApplication.class)
@SpringBootTest
public class CmsPageRepositoryTest {
    @Autowired
    private CmsPageRepository cmsPageRepository;

    /**
     * 分页查询
     */
    @Test
    public void testFindByPage() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<CmsPage> all = this.cmsPageRepository.findAll(pageRequest);
        // Assert.assertNotEquals(0, all.getTotalElements());
        Assert.assertTrue("查询所有的页面信息", all.getTotalElements() > 0);
    }

    /**
     * 添加
     */
    @Test
    public void testInsert() {
        // 定义实体类
        CmsPage cmsPage = new CmsPage();
        // 封装数据
        cmsPage.setPageName("测试页面");
        // 添加
        this.cmsPageRepository.save(cmsPage);
    }

    /**
     * 删除
     */
    @Test
    public void testDelete() {
        this.cmsPageRepository.deleteById("5ecf72a727817f0d585c2453");
    }

    /**
     * 更新
     */
    @Test
    public void testUpdate() {
        // 查询
        Optional<CmsPage> optional = this.cmsPageRepository.findById("5ecf738527817f23a08761ff");
        // 判断
        if (optional.isPresent()) {
            CmsPage cmsPage = optional.get();
            // 修改name
            cmsPage.setPageName("廖萍傻逼");
            // 更新数据库
            this.cmsPageRepository.save(cmsPage);
        }
    }
}