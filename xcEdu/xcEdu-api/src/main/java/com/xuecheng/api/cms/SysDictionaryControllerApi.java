package com.xuecheng.api.cms;

import com.xuecheng.model.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author: HuangMuChen
 * @date: 2020/6/22 13:17
 * @version: V1.0
 * @Description: SysDictionary相关的对外暴露的接口：在CMS服务工程编写Controller类实现此接口
 */
@Api(value = "数据字典管理", description = "数据字典管理接口,提供数据字典查询、新增、修改功能", tags = "SysDictionaryApi")
public interface SysDictionaryControllerApi {

    /**
     * 根据dType获取数据字典
     *
     * @param dType
     * @return
     */
    @ApiOperation("根据dType获取数据字典")
    @ApiImplicitParam(name = "dType", value = "字典分类", required = true, paramType = "path", dataType = "String")
    SysDictionary getByType(String dType);
}