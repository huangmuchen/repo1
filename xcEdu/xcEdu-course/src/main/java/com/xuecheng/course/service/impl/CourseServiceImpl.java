package com.xuecheng.course.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.common.constant.CourseConstant;
import com.xuecheng.common.exception.CustomException;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.QueryResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.course.dao.mapper.CourseMapper;
import com.xuecheng.course.dao.repository.CourseBaseRepository;
import com.xuecheng.course.dao.repository.CoursePicRepository;
import com.xuecheng.course.dao.repository.TeachplanRepository;
import com.xuecheng.course.service.ICourseService;
import com.xuecheng.model.domain.course.CourseBase;
import com.xuecheng.model.domain.course.Teachplan;
import com.xuecheng.model.domain.course.ext.CourseInfo;
import com.xuecheng.model.domain.course.ext.TeachplanNode;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.xuecheng.common.model.response.CommonCode.INVALID_PARAM;

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
    @Autowired
    private TeachplanRepository teachplanRepository;

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
            if (courseListRequest == null) {
                courseListRequest = new CourseListRequest(); // 初始化查询条件
            }
            if (page <= 0) {
                page = 0; // 初始化page，mysql默认从第0开始索引页
            }
            if (size <= 0) {
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

    /**
     * 根据课程id查询课程计划列表
     *
     * @param courseId
     * @return
     */
    @Override
    public TeachplanNode findTeachplanList(String courseId) {
        // 调用mapper层进行查询
        return this.courseMapper.findTeachplanList(courseId);
    }

    /**
     * 添加课程计划
     *
     * @param teachplan
     * @return
     */
    @Override
    public ResponseResult addTeachplan(Teachplan teachplan) {
        // 参数判断
        if (teachplan == null || StringUtils.isEmpty(teachplan.getCourseid()) || StringUtils.isEmpty(teachplan.getPname())) {
            throw new CustomException(INVALID_PARAM);
        }
        // 获取课程id
        String courseid = teachplan.getCourseid();
        // 获取父节点id
        String parentid = teachplan.getParentid();
        // 如果父节点id为空，说明上级结点为根结点
        if (StringUtils.isEmpty(parentid)) {
            // 获取课程计划根结点id
            parentid = getTeachplanRoot(courseid);
        }
        // 获取父节点信息
        Teachplan parent = this.teachplanRepository.getOne(parentid);
        // 获取父节点等级
        String parentGrade = parent.getGrade();
        // 计算当前课程计划的等级
        /*if (CourseConstant.GRADE_ONE.equals(parentGrade)){
            teachplan.setGrade(CourseConstant.GRADE_TWO);
        }else if (CourseConstant.GRADE_TWO.equals(parentGrade)){
            teachplan.setGrade(CourseConstant.GRADE_THREE);
        }*/
        String currGrade = String.valueOf(Integer.parseInt(parentGrade) + 1);
        // 设置课程计划等级
        teachplan.setGrade(currGrade);
        // 设置父节点id
        teachplan.setParentid(parentid);
        // 保存
        this.teachplanRepository.save(teachplan);
        // 响应成功结果
        return ResponseResult.SUCCESS();
    }

    /**
     * 获取根结点，如果是新课，那么根结点不存在，需要创建根结点
     *
     * @param courseid
     * @return
     */
    public String getTeachplanRoot(String courseid) {
        // 查询课程基本信息
        CourseBase courseBase = this.courseBaseRepository.getOne(courseid);
        // 根据courseid和parentid获取根结点
        List<Teachplan> teachplanList = this.teachplanRepository.findByCourseidAndParentid(courseid, CourseConstant.TEACHPLAN_ROOT_PARENTID);
        // 判断，如果不存在，则为新课，需添加根结点
        if (CollectionUtils.isEmpty(teachplanList)) {
            // 构建根节点对象
            Teachplan root = new Teachplan();
            root.setPname(courseBase.getName());
            root.setCourseid(courseid);
            root.setDescription(courseBase.getDescription());
            root.setParentid(CourseConstant.TEACHPLAN_ROOT_PARENTID); // "0"
            root.setGrade(CourseConstant.GRADE_ONE); // 一级结点
            root.setStatus(CourseConstant.TEACHPLAN_STATUS_NO); // "0"(未发布)
            // 保存数据
            Teachplan save = this.teachplanRepository.save(root);
            // 返回根节点id
            return save.getId();
        }
        // 直接返回根节点id
        return teachplanList.get(0).getId();
    }
}