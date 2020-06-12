package com.xuecheng.cms.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.cms.dao.CmsPageRepository;
import com.xuecheng.cms.dao.CmsTemplateRepository;
import com.xuecheng.cms.service.ICmsPageService;
import com.xuecheng.common.exception.CustomException;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.QueryResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.cms.CmsPage;
import com.xuecheng.model.domain.cms.CmsTemplate;
import com.xuecheng.model.domain.cms.request.QueryPageRequest;
import com.xuecheng.model.domain.cms.response.CmsCode;
import com.xuecheng.model.domain.cms.response.CmsPageResult;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

/**
 * @author: HuangMuChen
 * @date: 2020/5/28 13:52
 * @version: V1.0
 * @Description: 页面信息业务层实现类
 */
@Service
public class CmsPageServiceImpl implements ICmsPageService {
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
            // 分页自定义查询
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
            // 插入成功返回的对象就是插入的对象(缺主键值)
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
     * 基于ftl模板字符串生成html文件
     *
     * @param templateFile
     * @param model
     * @return
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
     * @param pageId
     * @return
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
     * @param pageId
     * @return
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
}