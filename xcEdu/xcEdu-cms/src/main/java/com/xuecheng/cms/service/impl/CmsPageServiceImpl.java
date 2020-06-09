package com.xuecheng.cms.service.impl;

import com.xuecheng.cms.dao.CmsPageRepository;
import com.xuecheng.cms.service.ICmsPageService;
import com.xuecheng.common.exception.CustomException;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.QueryResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.cms.CmsPage;
import com.xuecheng.model.domain.cms.request.QueryPageRequest;
import com.xuecheng.model.domain.cms.response.CmsCode;
import com.xuecheng.model.domain.cms.response.CmsPageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
                size = 10;
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
            throw new CustomException(CmsCode.CMS_ADDPAGE_PARAM_ERROR);
        }
        // 校验页面是否存在：根据页面名称、站点Id、页面webpath查询
        CmsPage page = this.cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        // 判断
        if (page != null) {
            throw new CustomException(CmsCode.CMS_ADDPAGE_EXISTS);
        }
        // 主键(pageId)由spring data自动生成
        cmsPage.setPageId(null);
        // 保存数据到mongodb
        CmsPage save = this.cmsPageRepository.save(cmsPage);
        // 判断
        if (save == null) {
            // 保存失败抛出异常
            throw new CustomException(CmsCode.CMS_SAVEPAGE_ERROR);
        }
        // 返回保存成功结果
        return new CmsPageResult(CommonCode.SUCCESS, save);
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
            throw new CustomException(CmsCode.CMS_FINDPAGE_NOTEXIST);
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
        CmsPage save = this.cmsPageRepository.save(page);
        // 判断
        if (save == null) {
            // 保存失败抛出异常
            throw new CustomException(CmsCode.CMS_SAVEPAGE_ERROR);
        }
        // 返回保存成功结果
        return new CmsPageResult(CommonCode.SUCCESS, save);
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
            throw new CustomException(CmsCode.CMS_FINDPAGE_NOTEXIST);
        }
        // 调用dao层进行删除
        this.cmsPageRepository.deleteById(pageId); // delete(page)也可以
        // 返回删除结果
        return ResponseResult.SUCCESS();
    }
}