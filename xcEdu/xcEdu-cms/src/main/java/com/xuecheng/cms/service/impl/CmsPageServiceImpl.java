package com.xuecheng.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.cms.dao.CmsPageRepository;
import com.xuecheng.cms.dao.CmsSiteRepository;
import com.xuecheng.cms.dao.CmsTemplateRepository;
import com.xuecheng.cms.service.ICmsPageService;
import com.xuecheng.common.exception.CustomException;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.QueryResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.cms.CmsPage;
import com.xuecheng.model.domain.cms.CmsSite;
import com.xuecheng.model.domain.cms.CmsTemplate;
import com.xuecheng.model.domain.cms.request.QueryPageRequest;
import com.xuecheng.model.domain.cms.response.CmsCode;
import com.xuecheng.model.domain.cms.response.CmsPageResult;
import com.xuecheng.model.domain.cms.response.CmsPublishPageResult;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: HuangMuChen
 * @date: 2020/5/28 13:52
 * @version: V1.0
 * @Description: 页面信息业务层实现类
 */
@Slf4j
@Service
public class CmsPageServiceImpl implements ICmsPageService, ConfirmCallback, ReturnCallback {
    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private CmsSiteRepository cmsSiteRepository;
    // Cms页面发布交换机名称
    private static final String CMS_RELEASE_EXCHANGE = "xcEdu.cms.releasePage.exchange";

    /**
     * 分页查询页面信息
     *
     * @param page             页码
     * @param size             每页显示的条数
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    @Override
    public QueryResponseResult findCmsPageList(int page, int size, QueryPageRequest queryPageRequest) {
        try {
            // 初始化page
            if (page <= 0) {
                page = 1;
            }
            // 初始化size
            if (size <= 0) {
                size = 5;
            }
            // 初始化查询条件
            if (queryPageRequest == null) {
                queryPageRequest = new QueryPageRequest();
            }
            // 条件匹配器，页面名称和页面别名模糊查询(%{pageName}%、%{pageAliase}%)，需要自定义字符串的匹配器实现模糊查询
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains())
                    .withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());
            // 构建查询CmsPage
            CmsPage cmsPage = new CmsPage();
            // 校验并设置查询条件
            checkAndSet(cmsPage, queryPageRequest);
            // 构建条件实例
            Example<CmsPage> example = Example.of(cmsPage, matcher);
            // 封装分页对象,默认从0开始索引页(为了适应mongodb的接口将页码减1)
            PageRequest pageRequest = PageRequest.of(page - 1, size);
            // 自定义分页查询
            Page<CmsPage> all = this.cmsPageRepository.findAll(example, pageRequest);
            // 构建返回结果对象,并封装查询结果
            QueryResult<CmsPage> queryResult = new QueryResult<>();
            // 设置页面列表
            queryResult.setList(all.getContent());
            // 设置总条数
            queryResult.setTotal(all.getTotalElements());
            // 返回查询成功结果
            return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        } catch (Exception e) {
            e.printStackTrace();
            // 返回查询失败结果
            return new QueryResponseResult(CommonCode.FAIL, new QueryResult<CmsPage>());
        }
    }

    /**
     * 设置查询条件
     *
     * @param cmsPage          查询类
     * @param queryPageRequest 查询条件
     */
    private void checkAndSet(CmsPage cmsPage, QueryPageRequest queryPageRequest) {
        // 设置站点id作为查询条件
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        // 设置模板id作为查询条件
        if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        // 设置页面别名作为查询条件
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        // 设置页面名称作为查询条件
        if (StringUtils.isNotBlank(queryPageRequest.getPageName())) {
            cmsPage.setPageName(queryPageRequest.getPageName());
        }
        // 设置页面类型作为查询条件
        if (StringUtils.isNotBlank(queryPageRequest.getPageType())) {
            cmsPage.setPageType(queryPageRequest.getPageType());
        }
        // 设置页面ID作为查询条件
        if (StringUtils.isNotBlank(queryPageRequest.getPageId())) {
            cmsPage.setPageId(queryPageRequest.getPageId());
        }
    }

    /**
     * 页面添加
     *
     * @param cmsPage 新增页面
     * @return 新增结果
     */
    @Override
    public CmsPageResult add(CmsPage cmsPage) {
        // 校验cmsPage是否为空
        if (cmsPage == null) {
            // 非法参数异常
            throw new CustomException(CmsCode.CMS_ADD_PAGEDATA_ISNULL);
        }
        // 校验页面是否存在：根据页面名称、站点Id、页面webpath查询
        CmsPage page = this.cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        // 判断
        if (page != null) {
            throw new CustomException(CmsCode.CMS_ADD_PAGE_EXISTS);
        }
        // 主键(pageId)由spring data自动生成
        cmsPage.setPageId(null);
        // 保存数据到mongodb
        try {
            // 插入成功返回的对象就是插入的对象
            CmsPage save = this.cmsPageRepository.save(cmsPage);
            // 返回保存成功结果
            return new CmsPageResult(CommonCode.SUCCESS, save);
        } catch (Exception e) {
            // 保存失败抛出异常
            throw new CustomException(CmsCode.CMS_SAVE_PAGE_ERROR);
        }
    }

    /**
     * 根据页面id查询页面
     *
     * @param pageId 页面id
     * @return 查询结果
     */
    @Override
    public CmsPage findByPageId(String pageId) {
        // 调用dao层进行查询
        Optional<CmsPage> optional = this.cmsPageRepository.findById(pageId);
        // 返回查询结果
        return optional.orElse(null);
    }

    /**
     * 页面修改
     *
     * @param pageId  页面id
     * @param cmsPage 要修改的值
     * @return 更新结果
     */
    @Override
    public CmsPageResult update(String pageId, CmsPage cmsPage) {
        // 根据id查询页面信息
        CmsPage page = this.findByPageId(pageId);
        // 存在才更新
        if (page == null) {
            throw new CustomException(CmsCode.CMS_FIND_PAGE_NOTEXIST);
        }
        // 更新数据
        page.setTemplateId(cmsPage.getTemplateId());
        page.setSiteId(cmsPage.getSiteId());
        page.setPageName(cmsPage.getPageName());
        page.setPageAliase(cmsPage.getPageAliase());
        page.setPageWebPath(cmsPage.getPageWebPath());
        page.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
        page.setDataUrl(cmsPage.getDataUrl());
        page.setPageType(cmsPage.getPageType());
        page.setPageCreateTime(cmsPage.getPageCreateTime());
        // 保存更新后的数据到mongodb
        try {
            CmsPage save = this.cmsPageRepository.save(page);
            // 返回保存成功结果
            return new CmsPageResult(CommonCode.SUCCESS, save);
        } catch (Exception e) {
            // 保存失败抛出异常
            throw new CustomException(CmsCode.CMS_SAVE_PAGE_ERROR);
        }
    }

    /**
     * 根据pageId删除页面
     *
     * @param pageId 页面id
     * @return 删除结果
     */
    @Override
    public ResponseResult del(String pageId) {
        // 根据id查询页面信息
        CmsPage page = this.findByPageId(pageId);
        // 存在才删除
        if (page == null) {
            throw new CustomException(CmsCode.CMS_FIND_PAGE_NOTEXIST);
        }
        // 调用dao层进行删除，使用delete(page)也可以
        this.cmsPageRepository.deleteById(pageId);
        // 返回删除结果
        return ResponseResult.SUCCESS();
    }

    /**
     * 页面预览
     *
     * @param pageId 页面id
     * @return htmlString
     */
    @Override
    public String preview(String pageId) {
        // 1. 获取页面模型数据
        Map model = getModelByPageId(pageId);
        // 判断
        if (CollectionUtils.isEmpty(model)) {
            // 获取页面模型数据为空
            throw new CustomException(CmsCode.CMS_GENERATEHTML_DATA_ISNULL);
        }
        // 2. 获取页面模板(.ftl)
        String templateFile = getTemplateFileByPageId(pageId);
        // 判断
        if (StringUtils.isBlank(templateFile)) {
            // 模板文件不存在
            throw new CustomException(CmsCode.CMS_GENERATEHTML_TEMPLATEFILE_ISNULL);
        }
        // 3. 执行页面静态化
        String html = generateHtml(templateFile, model);
        // 判断
        if (StringUtils.isBlank(html)) {
            throw new CustomException(CmsCode.CMS_GENERATEHTML_HTML_ISNULL);
        }
        return html;
    }

    /**
     * 根据pageId发布页面
     *
     * @param pageId 页面id
     * @return 发布结果
     */
    @Override
    public ResponseResult release(String pageId) {
        // 根据pageId获取html文件
        String html = this.preview(pageId);
        // 保存html文件到GridFS
        saveHtml(pageId, html);
        // 发送消息到RabbitMQ
        sendMsgToMq(pageId, "release");
        // 响应发布结果
        return ResponseResult.SUCCESS();
    }

    /**
     * 根据pageId撤销页面发布
     *
     * @param pageId 页面id
     * @return 撤销结果
     */
    @Override
    public ResponseResult rollBack(String pageId) {
        // 发送消息到RabbitMQ
        sendMsgToMq(pageId, "rollBack");
        // 响应撤销结果
        return ResponseResult.SUCCESS();
    }

    /**
     * 添加Page（课程详情页），提供给课程服务调用，如果已经有了则更新
     *
     * @param cmsPage
     * @return
     */
    @Override
    public CmsPageResult saveCoursePage(CmsPage cmsPage) {
        // 校验课程页面是否存在：根据页面名称、站点Id、页面webpath查询
        CmsPage coursePage = this.cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        // 校验
        if (coursePage == null) {
            // 不存在，添加
            return this.add(cmsPage);
        } else {
            // 存在，更新
            return this.update(coursePage.getPageId(), cmsPage);
        }
    }

    /**
     * 一键发布页面
     *
     * @param cmsPage
     * @return
     */
    @Override
    public CmsPublishPageResult publishPageQuick(CmsPage cmsPage) {
        // 将页面信息添加到数据库（mongodb）
        CmsPageResult cmsPageResult = this.saveCoursePage(cmsPage);
        // 判断添加结果
        if (!cmsPageResult.isSuccess()) {
            return new CmsPublishPageResult(CommonCode.FAIL, null);
        }
        // 获取添加成功后的pageId
        String pageId = cmsPageResult.getCmsPage().getPageId();
        // 根据pageId发布页面
        ResponseResult responseResult = this.release(pageId);
        // 判断发布结果
        if (!responseResult.isSuccess()) {
            return new CmsPublishPageResult(CommonCode.FAIL, null);
        }
        // 获取站点id
        String siteId = cmsPageResult.getCmsPage().getSiteId();
        // 查询站点信息
        Optional<CmsSite> optional = this.cmsSiteRepository.findById(siteId);
        // 判断查询结果
        if (!optional.isPresent()) {
            return new CmsPublishPageResult(CommonCode.FAIL, null);
        }
        /*
         * pageUrl = 站点域名 + 站点webpath + 页面webpath + 页面名称
         *
         * 站点域名：http://www.xuecheng.com
         * 站点webpath：空
         * 页面webpath：/course/detail/
         * 页面名称：297e7c7c62b888f00162b8a7dec20000.html
         */
        String pageUrl = optional.get().getSiteDomain() + optional.get().getSiteWebPath() + cmsPageResult.getCmsPage().getPageWebPath() + cmsPageResult.getCmsPage().getPageName();
        // 返回页面url
        return new CmsPublishPageResult(CommonCode.SUCCESS, pageUrl);
    }

    /**
     * 保存html文件到GridFS
     *
     * @param pageId 页面id
     * @param html   静态文件
     */
    private void saveHtml(String pageId, String html) {
        // 根据id查询cmsPage
        CmsPage cmsPage = this.findByPageId(pageId);
        // 判断
        if (cmsPage == null) {
            throw new CustomException(CmsCode.CMS_FIND_PAGE_NOTEXIST);
        }
        // 获取htmlFileId
        String htmlFileId = cmsPage.getHtmlFileId();
        // 判断，存储之前先备份旧的静态文件
        if (StringUtils.isNotBlank(htmlFileId)) {
            if (StringUtils.isNotBlank(cmsPage.getPreHtmlFileId())) {
                this.gridFsTemplate.delete(Query.query(Criteria.where("_id").is(cmsPage.getPreHtmlFileId())));
            }
            cmsPage.setPreHtmlFileId(htmlFileId);
        }
        try {
            // 将html字符串转化为输入流
            InputStream in = IOUtils.toInputStream(html, StandardCharsets.UTF_8);
            // 保存html文件到GridFS，fileName为pageName
            ObjectId objectId = this.gridFsTemplate.store(in, cmsPage.getPageName());
            // 获取templateFileId作为htmlFileId
            cmsPage.setHtmlFileId(objectId.toString());
            // 更新cmsPage
            this.cmsPageRepository.save(cmsPage);
        } catch (Exception e) {
            throw new CustomException(CmsCode.CMS_GENERATEHTML_SAVEHTML_ERROR);
        }
    }

    /**
     * 基于ftl模板字符串生成html文件
     *
     * @param templateFile 模板文件
     * @param model        数据模型
     * @return html静态文件
     */
    private String generateHtml(String templateFile, Map model) {
        try {
            // 创建配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            // 模板加载器
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template", templateFile);
            // 配置模板加载器
            configuration.setTemplateLoader(stringTemplateLoader);
            // 得到模板
            Template template = configuration.getTemplate("template", "utf-8");
            // 静态化
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据页面id获取页面模板文件(.ftl)
     *
     * @param pageId 页面id
     * @return 模板文件
     */
    private String getTemplateFileByPageId(String pageId) {
        // 根据pageId获取cmsPage
        CmsPage cmsPage = findByPageId(pageId);
        // 判断页面是否存在
        if (cmsPage == null) {
            throw new CustomException(CmsCode.CMS_FIND_PAGE_NOTEXIST);
        }
        // 获取模板id
        String templateId = cmsPage.getTemplateId();
        // 根据模板id获取模板信息
        Optional<CmsTemplate> optional = this.cmsTemplateRepository.findById(templateId);
        // 判断
        if (!optional.isPresent()) {
            // 页面模板不存在
            throw new CustomException(CmsCode.CMS_FIND_TEMPLATE_NOTEXIST);
        }
        // 获取模板文件id
        String templateFileId = optional.get().getTemplateFileId();
        try {
            // 根据id获取模板文件
            GridFSFile gridFSFile = this.gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            // 打开下载流对象
            GridFSDownloadStream gridFSDownloadStream = this.gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            // 创建gridFsResource，用于获取流对象
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            // 获取流中的数据
            return IOUtils.toString(gridFsResource.getInputStream(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取数据模型
     *
     * @param pageId 页面id
     * @return 数据模型
     */
    private Map getModelByPageId(String pageId) {
        // 根据pageId获取cmsPage
        CmsPage cmsPage = findByPageId(pageId);
        // 判断页面是否存在
        if (cmsPage == null) {
            throw new CustomException(CmsCode.CMS_FIND_PAGE_NOTEXIST);
        }
        // 获取dataUrl
        String dataUrl = cmsPage.getDataUrl();
        // 判断
        if (StringUtils.isBlank(dataUrl)) {
            throw new CustomException(CmsCode.CMS_GENERATEHTML_DATAURL_ISNULL);
        }
        // 根据dataUrl远程获取数据模型  TODO：如果jwt存在，拼接请求头认证
        ResponseEntity<Map> entity = this.restTemplate.getForEntity(dataUrl, Map.class);
        // 返回查询结果
        return entity.getBody();
    }

    /**
     * 发送消息到RabbitMQ
     *
     * @param pageId 页面id
     */
    public void sendMsgToMq(String pageId, String type) {
        // 根据id查询cmsPage
        CmsPage cmsPage = this.findByPageId(pageId);
        // 校验cmsPage
        if (cmsPage == null) {
            throw new CustomException(CmsCode.CMS_FIND_PAGE_NOTEXIST);
        }
        // 获取站点id作为routingKey
        String siteId = cmsPage.getSiteId();
        // 设置消息内容为页面ID。（采用json格式，方便日后扩展）
        Map<String, String> map = new HashMap<>();
        map.put("pageId", pageId);
        map.put("type", type);
        // 消息内容
        String msg = JSON.toJSONString(map);
        // 开启强制委托模式
        this.rabbitTemplate.setMandatory(true);
        // 设置消息发送给交换机的回调
        this.rabbitTemplate.setConfirmCallback(this);
        // 设置消息从交换机发送给队列的回调
        this.rabbitTemplate.setReturnCallback(this);
        // 关联数据
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 发送消息给交换机(如果不指定，将发给.yml文件中默认的交换机))，并将站点id作为routingKey
        this.rabbitTemplate.convertAndSend(CMS_RELEASE_EXCHANGE, siteId, msg, correlationData);
    }

    /**
     * 消息是否成功到达交换机
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            // 消息成功到达交换机
            log.info("回调消息ID为：{}，消息成功发送到交换机！", correlationData.getId());
        } else {
            // 消息到达交换机失败
            log.error("回调消息ID为：{}，消发送到交换机失败！原因：[{}]", correlationData.getId(), cause);
        }
    }

    /**
     * 消息是否成功从交换机到达队列：成功，则returnedMessage方法不会执行，失败，returnedMessage方法会执行
     *
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("发送消息：{}，错误码：{}，错误原因：{}，exchange：{}，routingKey：{}", new String(message.getBody(), StandardCharsets.UTF_8), replyCode, replyText, exchange, routingKey);
    }
}