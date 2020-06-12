package com.xuecheng.cms.service.impl;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.cms.dao.CmsPageRepository;
import com.xuecheng.cms.dao.CmsTemplateRepository;
import com.xuecheng.cms.service.ICmsTemplateService;
import com.xuecheng.common.exception.CustomException;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.QueryResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.model.domain.cms.CmsPage;
import com.xuecheng.model.domain.cms.CmsTemplate;
import com.xuecheng.model.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.model.domain.cms.response.CmsCode;
import com.xuecheng.model.domain.cms.response.CmsTemplateResult;
import com.xuecheng.model.domain.cms.response.CmsUploadResult;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * @author: HuangMuChen
 * @date: 2020/6/6 23:58
 * @version: V1.0
 * @Description: 页面模板业务层实现类
 */
@Service
public class CmsTemplateServiceImpl implements ICmsTemplateService {
    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private CmsPageRepository cmsPageRepository;

    /**
     * 查询模板列表
     *
     * @return
     */
    @Override
    public QueryResponseResult findCmsTemplateList() {
        try {
            // 调用dao层进行查询
            List<CmsTemplate> cmsTemplateList = this.cmsTemplateRepository.findAll();
            // 构建返回结果对象
            QueryResult<CmsTemplate> queryResult = new QueryResult<>();
            // 封装查询结果
            queryResult.setList(cmsTemplateList);
            queryResult.setTotal(cmsTemplateList.size());
            // 返回查询成功结果
            return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        } catch (Exception e) {
            e.printStackTrace();
            // 返回查询失败结果
            return new QueryResponseResult(CommonCode.FAIL, new QueryResult<CmsTemplate>());
        }
    }

    /**
     * 分页查询模板列表
     *
     * @param page
     * @param size
     * @param queryTemplateRequest
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryTemplateRequest queryTemplateRequest) {
        try {
            // 初始化page
            if (page <= 0) {
                page = 1;
            }
            // 初始化size
            if (size <= 0) {
                size = 5;
            }
            // 防止空指针异常判断查询条件对象的值是否为null
            if (queryTemplateRequest == null) {
                queryTemplateRequest = new QueryTemplateRequest();
            }
            // 定义条件匹配器(模板名称模糊查询)
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("templateName", ExampleMatcher.GenericPropertyMatchers.contains());
            // 定义条件值对象
            CmsTemplate cmsTemplate = new CmsTemplate();
            // 设置条件值（站点ID）
            if (StringUtils.isNotEmpty(queryTemplateRequest.getSiteId())) {
                // 站点Id不为空，设置值
                cmsTemplate.setSiteId(queryTemplateRequest.getSiteId());
            }
            // 设置条件值(模板名称)
            if (StringUtils.isNotEmpty(queryTemplateRequest.getTemplateName())) {
                // 模板名称不为空，设置值
                cmsTemplate.setTemplateName(queryTemplateRequest.getTemplateName());
            }
            // 定义条件对象
            Example<CmsTemplate> example = Example.of(cmsTemplate, matcher);
            // 创建分页对象
            Pageable pageable = PageRequest.of(page - 1, size);
            // 调用DAO传入分页参数以及自定义查询条件获取返回结果
            Page<CmsTemplate> all = this.cmsTemplateRepository.findAll(example, pageable);
            // 创建QueryResult对象
            QueryResult<CmsTemplate> queryResultTemplate = new QueryResult<>();
            // 设置模板列表
            queryResultTemplate.setList(all.getContent());
            // 设置数据总记录数
            queryResultTemplate.setTotal(all.getTotalElements());
            // 创建方法需要返回的对象(传入状态码与数据)
            return new QueryResponseResult(CommonCode.SUCCESS, queryResultTemplate);
        } catch (Exception e) {
            e.printStackTrace();
            // 返回查询失败结果
            return new QueryResponseResult(CommonCode.FAIL, new QueryResult<CmsTemplate>());
        }
    }

    /**
     * 模板文件的上传(新增时)
     *
     * @param file
     * @return
     */
    public CmsUploadResult uploadTemplateFile(MultipartFile file) {
        // 文件为空
        if (file == null) {
            throw new CustomException(CmsCode.CMS_UPLOAD_TEMPLATEFILE_ISNULL);
        }
        // 把文件存储到GridFs中
        try {
            // 获取输入流
            InputStream in = file.getInputStream();
            // 向GridFS存储文件
            ObjectId objectId = this.gridFsTemplate.store(in, file.getOriginalFilename(), "");
            // 得到文件ID
            String templateFileId = objectId.toString();
            // 保存成功返回模板文件id
            return new CmsUploadResult(CommonCode.SUCCESS, templateFileId);
        } catch (Exception e) {
            // 保存失败抛出异常
            throw new CustomException(CmsCode.CMS_SAVE_TEMPLATEFILE_ERROR);
        }
    }

    /**
     * 模板文件上传（修改时）
     *
     * @param templateFileId
     * @param file
     * @return
     */
    public CmsUploadResult editUploadTemplateFile(String templateFileId, MultipartFile file) {
        // 根据templateFileId查询模板文件
        GridFSFile gridFSFile = this.gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
        // 此模板下有模板文件，先删除
        if (gridFSFile != null) {
            this.deleteTemplateFile(templateFileId);
        }
        // 调用上传文件方法进行存入，并返回模板文件ID
        return this.uploadTemplateFile(file);
    }

    /**
     * 删除刚刚上传的模板文件
     *
     * @param templateFileId
     * @return
     */
    public CmsUploadResult deleteTemplateFile(String templateFileId) {
        // 根据templateFileId查询模板文件
        GridFSFile gridFSFile = this.gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
        // 存在才删除
        if (gridFSFile == null) {
            throw new CustomException(CmsCode.CMS_DELETE_TEMPLATEFILE_ISNULL);
        }
        // 根据文件id删除fs.files和fs.chunks中的记录
        this.gridFsTemplate.delete(Query.query(Criteria.where("_id").is(templateFileId)));
        // 返回删除结果
        return new CmsUploadResult(CommonCode.SUCCESS, "删除成功");
    }

    /**
     * 新增模板
     *
     * @param cmsTemplate
     * @return
     */
    public CmsTemplateResult add(CmsTemplate cmsTemplate) {
        // 校验cmsTemplate是否为空
        if (cmsTemplate == null) {
            throw new CustomException(CmsCode.CMS_ADD_TEMPLATEDATA_ISNULL);
        }
        // 主键(templateId)由spring data自动生成
        cmsTemplate.setTemplateId(null);
        // 保存数据到mongodb
        try {
            // 插入成功返回的对象就是插入的对象(缺主键值)
            CmsTemplate save = this.cmsTemplateRepository.save(cmsTemplate);
            // 返回成功信息
            return new CmsTemplateResult(CommonCode.SUCCESS, save);
        } catch (Exception e) {
            // 保存失败抛出异常
            throw new CustomException(CmsCode.CMS_SAVE_TEMPLATE_ERROR);
        }
    }

    /**
     * 删除模板（及其模板文件）
     *
     * @param templateId
     * @return
     */
    public ResponseResult deleteTemplate(String templateId) {
        // 根据模板ID获取模板
        Optional<CmsTemplate> optional = this.cmsTemplateRepository.findById(templateId);
        // 存在才删除
        if (!optional.isPresent()) {
            throw new CustomException(CmsCode.CMS_DELETE_TEMPLATE_ISFALL);
        }
        // 不为空，首先获取此模板下是否有page页面与其对应，没有的话可以删除，否则抛异常
        List<CmsPage> cmsPageList = this.cmsPageRepository.findByTemplateId(templateId);
        if (!CollectionUtils.isEmpty(cmsPageList)) {
            // 表示此模板下包含页面，不能删除此模板
            throw new CustomException(CmsCode.CMS_TEMPLATE_HAVEPAGE);
        }
        // 获取模板文件ID
        String templateFileId = optional.get().getTemplateFileId();
        // 根据模板文件ID删除GridFs中的模板文件
        GridFSFile gridFSFile = this.gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
        // 存在，就删除
        if (gridFSFile != null) {
            this.deleteTemplateFile(templateFileId);
        }
        // 根据模板ID删除对应的模板
        this.cmsTemplateRepository.deleteById(templateId);
        // 返回删除结果
        return ResponseResult.SUCCESS();
    }

    /**
     * 根据模板ID获取模板对象
     *
     * @param templateId
     * @return
     */
    public CmsTemplate findTemplateById(String templateId) {
        // 调用dao进行查询
        Optional<CmsTemplate> optional = this.cmsTemplateRepository.findById(templateId);
        // 返回查询结果
        return optional.orElse(null);
    }

    /**
     * 修改模板信息
     *
     * @param templateId
     * @param cmsTemplate
     * @return
     */
    public CmsTemplateResult updateTemplate(String templateId, CmsTemplate cmsTemplate) {
        // 根据id查询模板信息
        CmsTemplate template = this.findTemplateById(templateId);
        // 不为空，才更新数据
        if (template == null) {
            throw new CustomException(CmsCode.CMS_FIND_TEMPLATE_NOTEXIST);
        }
        // 更新数据
        template.setSiteId(cmsTemplate.getSiteId());
        template.setTemplateName(cmsTemplate.getTemplateName());
        template.setTemplateParameter(cmsTemplate.getTemplateParameter());
        template.setTemplateFileId(cmsTemplate.getTemplateFileId());
        // 保存更新后的数据到mongodb
        try {
            CmsTemplate save = this.cmsTemplateRepository.save(template);
            // 返回保存成功结果
            return new CmsTemplateResult(CommonCode.SUCCESS, save);
        } catch (Exception e) {
            // 保存失败抛出异常
            throw new CustomException(CmsCode.CMS_SAVE_TEMPLATE_ERROR);
        }
    }
}