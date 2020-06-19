package com.xuecheng.model.domain.course.request;

import com.xuecheng.common.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 请求模型：封装前端Course查询条件
 */
@Data
@ToString
public class CourseListRequest extends RequestData {
    private String companyId; // 公司Id
}