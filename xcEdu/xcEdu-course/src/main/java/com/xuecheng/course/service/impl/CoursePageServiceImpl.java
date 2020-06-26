package com.xuecheng.course.service.impl;

import com.xuecheng.common.exception.CustomException;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.course.client.CmsPageClient;
import com.xuecheng.course.config.CoursePublishProperties;
import com.xuecheng.course.dao.repository.CourseBaseRepository;
import com.xuecheng.course.service.ICoursePageService;
import com.xuecheng.model.domain.cms.CmsPage;
import com.xuecheng.model.domain.cms.response.CmsPageResult;
import com.xuecheng.model.domain.course.CourseBase;
import com.xuecheng.model.domain.course.response.CourseCode;
import com.xuecheng.model.domain.course.response.CoursePublishResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author: HuangMuChen
 * @date: 2020/6/26 23:31
 * @version: V1.0
 * @Description: 课程页面业务层实现类
 */
@Service
@EnableConfigurationProperties(CoursePublishProperties.class)
public class CoursePageServiceImpl implements ICoursePageService {
    @Autowired
    private CmsPageClient cmsPageClient;
    @Autowired
    private CoursePublishProperties prop;
    @Autowired
    private CourseBaseRepository courseBaseRepository;

    /**
     * 课程预览
     *
     * @param courseId
     * @return
     */
    @Override
    public CoursePublishResult coursePreview(String courseId) {
        // 根据课程信息创建CmsPage对象
        CmsPage cmsPage = toCmsPage(courseId);
        // 通过FeignClient远程调用CmsPage服务添加课程详情页面，并返回pageId
        CmsPageResult cmsPageResult = this.cmsPageClient.saveCoursePage(cmsPage);
        // 判断添加结果
        if (!cmsPageResult.isSuccess()) {
            return new CoursePublishResult(CommonCode.FAIL, null);
        }
        // 拼接课程预览url
        String previewUrl = prop.getPreviewUrlPrefix() + cmsPageResult.getCmsPage().getPageId();
        // 返回结果
        return new CoursePublishResult(CommonCode.SUCCESS, previewUrl);
    }

    /**
     * 将课程数据转为CmsPage对象
     *
     * @param courseId
     * @return
     */
    private CmsPage toCmsPage(String courseId) {
        // 根据课程id查询课程基础信息
        Optional<CourseBase> optional = this.courseBaseRepository.findById(courseId);
        // 校验查询结果
        if (!optional.isPresent()) {
            throw new CustomException(CourseCode.COURSE_BASE_NOTEXIST);
        }
        // 创建课程页面对象
        CmsPage cmsPage = new CmsPage();
        // 设置站点id
        cmsPage.setSiteId(prop.getSiteId());
        // 设置模板id
        cmsPage.setTemplateId(prop.getTemplateId());
        // 设置页面名称
        cmsPage.setPageName(courseId + ".html");
        // 设置页面别名
        cmsPage.setPageAliase(optional.get().getName());
        // 设置dataUrl
        cmsPage.setDataUrl(prop.getDataUrlPrefix() + courseId);
        // 设置页面web路径
        cmsPage.setPageWebPath(prop.getPageWebPath());
        // 设置页面物理路径
        cmsPage.setPagePhysicalPath(prop.getPagePhysicalPath());
        // 设置创建时间
        cmsPage.setPageCreateTime(new Date());
        // 设置页面类型（"0"->静态）
        cmsPage.setPageType("0");
        // 返回封装对象
        return cmsPage;
    }
}