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
public class CategoryNode extends Category {
    List<CategoryNode> children;
}