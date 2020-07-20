package com.xuecheng.model.domain.course.request;

import com.xuecheng.common.model.request.RequestData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "CourseListRequest", description = "封装查询条件")
public class CourseListRequest extends RequestData {
    @ApiModelProperty("公司Id")
    private String companyId;
    @ApiModelProperty("用户Id")
    private String userId;
}