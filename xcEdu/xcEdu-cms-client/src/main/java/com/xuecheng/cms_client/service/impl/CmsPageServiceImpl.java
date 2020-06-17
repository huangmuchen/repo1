package com.xuecheng.cms_client.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.xuecheng.cms_client.dao.CmsPageRepository;
import com.xuecheng.cms_client.dao.CmsSiteRepository;
import com.xuecheng.cms_client.service.ICmsPageService;
import com.xuecheng.model.domain.cms.CmsPage;
import com.xuecheng.model.domain.cms.CmsSite;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * @author: HuangMuChen
 * @date: 2020/6/15 21:52
 * @version: V1.0
 * @Description: 页面发布业务层实现类
 */
@Slf4j
@Service
public class CmsPageServiceImpl implements ICmsPageService {
    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private CmsSiteRepository cmsSiteRepository;
    @Autowired
    private GridFSBucket gridFSBucket;

    /**
     * 根据pageId，从GridFS中获取html保存到服务器指定路径上
     *
     * @param pageId 页面id
     */
    @Override
    public void savePageToServerPath(String pageId, String type) {
        // 根据pageId查询cmsPage
        CmsPage cmsPage = getCmsPageById(pageId);
        // 校验cmsPage
        if (cmsPage == null) {
            log.error("页面不存在, pageId：{}", pageId);
            return;
        }
        // 根据发布类型，获取静态文件id
        String fileId;
        if ("release".equals(type)) {
            fileId = cmsPage.getHtmlFileId();
        } else if ("rollBack".equals(type)) {
            fileId = cmsPage.getPreHtmlFileId();
        } else {
            log.error("发布类型错误，type：{}", type);
            return;
        }
        // 根据siteId查询站点
        CmsSite cmsSite = getCmsSiteById(cmsPage.getSiteId());
        // 页面物理路径 = 站点物理路径 + 页面物理路径 + 页面名称
        String savePath = cmsSite.getSitePhysicalPath() + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();
        FileOutputStream out = null;
        try {
            // 创建输出流
            out = new FileOutputStream(new File(savePath));
            // 从GridFS下载静态文件到服务器指定路径
            this.gridFSBucket.downloadToStream(new ObjectId(fileId), out);
        } catch (Exception e) {
            log.error("从GridFS下载静态文件到服务器指定路径出现异常，异常信息：{}", e.getMessage());
        } finally {
            // 关闭资源
            try {
                out.close();
            } catch (IOException e) {
                log.error("关闭资源异常，异常信息：{}", e.getMessage());
            }
        }
    }

    /**
     * 根据id获取CmsSite
     *
     * @param siteId 站点id
     * @return 站点信息
     */
    public CmsSite getCmsSiteById(String siteId) {
        Optional<CmsSite> optional = this.cmsSiteRepository.findById(siteId);
        return optional.orElse(null);
    }

    /**
     * 根据id获取CmsPage
     *
     * @param pageId 页面id
     * @return 页面信息
     */
    public CmsPage getCmsPageById(String pageId) {
        Optional<CmsPage> optional = this.cmsPageRepository.findById(pageId);
        return optional.orElse(null);
    }
}