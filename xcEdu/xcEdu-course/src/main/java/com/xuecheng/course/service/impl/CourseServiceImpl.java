package com.xuecheng.course.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.QueryResult;
import com.xuecheng.course.dao.mapper.CourseMapper;
import com.xuecheng.course.dao.repository.CourseBaseRepository;
import com.xuecheng.course.dao.repository.CoursePicRepository;
import com.xuecheng.course.service.ICourseService;
import com.xuecheng.model.domain.course.CourseBase;
import com.xuecheng.model.domain.course.ext.CourseInfo;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: HuangMuChen
 * @date: 2020/6/19 20:37
 * @version: V1.0
 * @Description: 课程信息业务层实现类
 */
@Service
public class CourseServiceImpl implements ICourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseBaseRepository courseBaseRepository;
    @Autowired
    private CoursePicRepository coursePicRepository;

    /**
     * 分页查询课程列表
     *
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    @Override
    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest) {
        try {
            if(courseListRequest == null){
                courseListRequest = new CourseListRequest(); // 初始化查询条件
            }
            if(page<=0){
                page = 0; // 初始化page，mysql默认从第0开始索引页
            }
            if(size<=0){
                size = 5; // 初始化size
            }
            // 设置分页
            PageHelper.startPage(page, size);
            // 调用mapper层进行查询
            Page<CourseInfo> pageList = this.courseMapper.findCourseList(courseListRequest);
            // 构建返回结果对象,并封装查询结果
            QueryResult<CourseInfo> queryResult = new QueryResult<>();
            // 设置课程列表
            queryResult.setList(pageList.getResult());
            // 设置总条数
            queryResult.setTotal(pageList.getTotal());
            // 返回查询成功结果
            return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        } catch (Exception e) {
            e.printStackTrace();
            // 返回查询失败结果
            return new QueryResponseResult(CommonCode.FAIL, new QueryResult<CourseBase>());
        }
    }
}