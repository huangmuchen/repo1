package com.xuecheng.cms.controller;

import com.xuecheng.api.cms.SysDictionaryControllerApi;
import com.xuecheng.cms.service.ISysDictionaryService;
import com.xuecheng.model.domain.system.SysDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: HuangMuChen
 * @date: 2020/6/22 13:25
 * @version: V1.0
 * @Description: 数据字典控制层
 */
@RestController
@RequestMapping("/sys/dictionary")
public class SysDictionaryController implements SysDictionaryControllerApi {
    @Autowired
    private ISysDictionaryService sysDictionaryService;

    /**
     * 根据dType获取数据字典
     *
     * @param dType
     * @return
     */
    @Override
    @GetMapping("/get/{dType}")
    public SysDictionary getByType(@PathVariable("dType") String dType) {
        // 调用Service层进行查询,并返回结果
        return this.sysDictionaryService.getByType(dType);
    }
}