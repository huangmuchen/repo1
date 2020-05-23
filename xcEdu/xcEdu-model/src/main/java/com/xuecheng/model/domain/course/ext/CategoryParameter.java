package com.xuecheng.model.domain.course.ext;

import com.xuecheng.model.domain.course.Category;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
public class CategoryParameter extends Category {
    List<String> bIds; // 二级分类ids
    List<String> cIds; // 三级分类ids
}