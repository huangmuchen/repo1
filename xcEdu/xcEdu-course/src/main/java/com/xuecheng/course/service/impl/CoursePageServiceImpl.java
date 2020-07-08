package com.xuecheng.course.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuecheng.common.constant.CourseConstant;
import com.xuecheng.common.exception.CustomException;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.course.client.CmsPageClient;
import com.xuecheng.course.config.CoursePublishProperties;
import com.xuecheng.course.dao.mapper.TeachplanMapper;
import com.xuecheng.course.dao.repository.CourseBaseRepository;
import com.xuecheng.course.dao.repository.CourseMarketRepository;
import com.xuecheng.course.dao.repository.CoursePicRepository;
import com.xuecheng.course.dao.repository.CoursePubRepository;
import com.xuecheng.course.service.ICoursePageService;
import com.xuecheng.model.domain.cms.CmsPage;
import com.xuecheng.model.domain.cms.response.CmsPageResult;
import com.xuecheng.model.domain.cms.response.CmsPublishPageResult;
import com.xuecheng.model.domain.course.CourseBase;
import com.xuecheng.model.domain.course.CourseMarket;
import com.xuecheng.model.domain.course.CoursePic;
import com.xuecheng.model.domain.course.CoursePub;
import com.xuecheng.model.domain.course.ext.TeachplanNode;
import com.xuecheng.model.domain.course.response.CourseCode;
import com.xuecheng.model.domain.course.response.CoursePublishResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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
    @Autowired
    private CoursePicRepository coursePicRepository;
    @Autowired
    private CourseMarketRepository courseMarketRepository;
    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private CoursePubRepository coursePubRepository;
    // 时间格式化对象
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
     * 课程发布
     *
     * @param courseId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CoursePublishResult coursePublish(String courseId) {
        // 根据课程信息创建CmsPage对象
        CmsPage cmsPage = toCmsPage(courseId);
        // 通过FeignClient远程调用CmsPage一键发布接口，并返回pageUrl
        CmsPublishPageResult cmsPublishPageResult = this.cmsPageClient.publishPageQuick(cmsPage);
        // 判断添加结果
        if (!cmsPublishPageResult.isSuccess()) {
            return new CoursePublishResult(CommonCode.FAIL, null);
        }
        // 更改课程状态为已发布
        updateCourseState(courseId);
        // 创建课程索引信息
        CoursePub coursePub = createCoursePub(courseId);
        // 保存课程索引信息到数据库，由Logstash自动更新索引
        saveCoursePub(coursePub);
        // TODO: 发布后，保存课程计划媒资信息到teachplan_media_pub表，方便Logstash同步到ES上

        // 获取页面url
        String pageUrl = cmsPublishPageResult.getPageUrl();
        // 返回发布结果
        return new CoursePublishResult(CommonCode.SUCCESS, pageUrl);
    }

    /**
     * 保存课程发布信息到数据库
     *
     * @param coursePub
     * @return
     */
    private void saveCoursePub(CoursePub coursePub) {
        // 创建要保存的课程发布对象
        CoursePub coursePubNew = new CoursePub();
        // 首先根据id查询，看看是否存在
        Optional<CoursePub> optional = this.coursePubRepository.findById(coursePub.getId());
        if (optional.isPresent()) {
            // 存在，则更新对象
            coursePubNew = optional.get();
        }
        // 复制数据
        BeanUtils.copyProperties(coursePub, coursePubNew);
        // 设置发布时间
        coursePubNew.setPubTime(this.sdf.format(new Date()));
        // 设置时间戳
        coursePubNew.setTimestamp(new Date());
        // 调用dao进行保存
        try {
            this.coursePubRepository.save(coursePubNew);
        } catch (Exception e) {
            throw new CustomException(CourseCode.COURSE_PUBLISH_CREATE_INDEX_ERROR);
        }
    }

    /**
     * 创建课程索引信息
     *
     * @param courseId
     * @return
     */
    private CoursePub createCoursePub(String courseId) {
        // 创建课程发布对象
        CoursePub coursePub = new CoursePub();
        // 设置主键
        coursePub.setId(courseId);
        // 封装课程基础信息
        Optional<CourseBase> courseBaseOptional = this.courseBaseRepository.findById(courseId);
        courseBaseOptional.ifPresent(courseBase -> BeanUtils.copyProperties(courseBase, coursePub));
        // 封装课程图片信息
        Optional<CoursePic> coursePicOptional = this.coursePicRepository.findById(courseId);
        coursePicOptional.ifPresent(coursePic -> BeanUtils.copyProperties(coursePic, coursePub));
        // 封装课程营销信息
        Optional<CourseMarket> courseMarketOptional = this.courseMarketRepository.findById(courseId);
        if (courseMarketOptional.isPresent()) {
            BeanUtils.copyProperties(courseMarketOptional.get(), coursePub);
            // 获取startTime和endTime
            Date startTime = courseMarketOptional.get().getStartTime();
            Date endTime = courseMarketOptional.get().getEndTime();
            // 如果startTime不为空，需转成字符串
            if (startTime != null) {
                coursePub.setStartTime(this.sdf.format(startTime));
            }
            // 如果endTime不为空，需转成字符串
            if (endTime != null) {
                coursePub.setEndTime(this.sdf.format(endTime));
            }
        }
        // 封装课程计划
        TeachplanNode teachplanNode = this.teachplanMapper.findTeachplanList(courseId);
        // 转成JSON字符串存储
        coursePub.setTeachplan(JSON.toJSONString(teachplanNode));
        // 返回coursePub对象
        return coursePub;
    }

    /**
     * 更新课程发布状态
     *
     * @param courseId
     * @return
     */
    private void updateCourseState(String courseId) {
        // 根据课程id查询课程基础信息
        CourseBase courseBase = getCourseBaseById(courseId);
        // 更新状态为：已发布
        courseBase.setStatus(CourseConstant.COURSEBASE_STATUS_YES);
        // 将更新后的数据保存到数据库，并返回更新结果
        this.courseBaseRepository.save(courseBase);
    }

    /**
     * 根据课程id查询课程基础信息
     *
     * @param courseId
     * @return
     */
    private CourseBase getCourseBaseById(String courseId) {
        // 根据课程id查询课程基础信息
        Optional<CourseBase> optional = this.courseBaseRepository.findById(courseId);
        // 校验查询结果
        if (!optional.isPresent()) {
            throw new CustomException(CourseCode.COURSE_BASE_NOTEXIST);
        }
        // 返回查询结果
        return optional.get();
    }

    /**
     * 将课程数据转为CmsPage对象
     *
     * @param courseId
     * @return
     */
    private CmsPage toCmsPage(String courseId) {
        // 根据课程id查询课程基础信息
        CourseBase courseBase = getCourseBaseById(courseId);
        // 创建课程页面对象
        CmsPage cmsPage = new CmsPage();
        // 设置站点id
        cmsPage.setSiteId(prop.getSiteId());
        // 设置模板id
        cmsPage.setTemplateId(prop.getTemplateId());
        // 设置页面名称
        cmsPage.setPageName(courseId + ".html");
        // 设置页面别名
        cmsPage.setPageAliase(courseBase.getName());
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