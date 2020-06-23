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
import com.xuecheng.course.dao.repository.CourseMarketRepository;
import com.xuecheng.course.dao.repository.CoursePicRepository;
import com.xuecheng.course.dao.repository.TeachplanRepository;
import com.xuecheng.course.service.ICourseService;
import com.xuecheng.model.domain.course.CourseBase;
import com.xuecheng.model.domain.course.CourseMarket;
import com.xuecheng.model.domain.course.CoursePic;
import com.xuecheng.model.domain.course.Teachplan;
import com.xuecheng.model.domain.course.ext.CourseInfo;
import com.xuecheng.model.domain.course.ext.TeachplanNode;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import com.xuecheng.model.domain.course.response.AddCourseResult;
import com.xuecheng.model.domain.course.response.CourseCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    private CourseMarketRepository courseMarketRepository;

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
    @Transactional(rollbackFor = Exception.class)
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
        Optional<Teachplan> optional = this.teachplanRepository.findById(parentid);
        // 校验父节点信息
        if (!optional.isPresent()) {
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
        Teachplan parent = optional.get();
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
     * 添加课程基础信息
     *
     * @param courseBase
     * @return
     */
    @Override
    // 在@Transactional注解中如果不配置rollbackFor属性,那么事物只会在遇到RuntimeException的时候才会回滚,加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚
    @Transactional(rollbackFor = Exception.class)
    public AddCourseResult addCourseBase(CourseBase courseBase) {
        // 设置课程状态为未发布
        courseBase.setStatus(CourseConstant.COURSEBASE_STATUS_NO);
        // TODO：将course_base表中的company_id改为非必填，待认证功能开发完成再修改为必填
        // 调用dao进行保存
        CourseBase save = this.courseBaseRepository.save(courseBase);
        // 返回课程id
        return new AddCourseResult(CommonCode.SUCCESS, save.getId());
    }

    /**
     * 查询课程基础信息
     *
     * @param courseId
     * @return
     */
    @Override
    public CourseBase getCourseBaseById(String courseId) {
        // 调用dao进行查询
        Optional<CourseBase> optional = this.courseBaseRepository.findById(courseId);
        // 返回查询结果
        return optional.orElse(null);
    }

    /**
     * 更新课程基础信息
     *
     * @param courseId
     * @param courseBase
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult updateCoursebase(String courseId, CourseBase courseBase) {
        // 根据课程id查询课程基础信息
        CourseBase cb = this.getCourseBaseById(courseId);
        // 校验课程基础信息
        if (cb == null) {
            throw new CustomException(CourseCode.COURSE_BASE_NOTEXIST);
        }
        // 封装数据
        cb.setName(courseBase.getName());
        cb.setMt(courseBase.getMt());
        cb.setSt(courseBase.getSt());
        cb.setGrade(courseBase.getGrade());
        cb.setStudymodel(courseBase.getStudymodel());
        cb.setUsers(courseBase.getUsers());
        cb.setDescription(courseBase.getDescription());
        try {
            // 更新数据到数据库
            this.courseBaseRepository.save(cb);
            // 返回更新成功结果
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            throw new CustomException(CourseCode.COURSE_BASE_UPDATE_ERROR);
        }
    }

    /**
     * 查询课程营销信息
     *
     * @param id
     * @return
     */
    @Override
    public CourseMarket getCourseMarketById(String id) {
        // 调用dao进行查询
        Optional<CourseMarket> optional = this.courseMarketRepository.findById(id);
        // 返回查询结果
        return optional.orElse(null);
    }

    /**
     * 更新课程营销信息
     *
     * @param id
     * @param courseMarket
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateCourseMarket(String id, CourseMarket courseMarket) {
        // 根据id查询课程营销信息
        CourseMarket cm = this.getCourseMarketById(id);
        // 校验课程营销信息
        if (cm == null) {
            // 新增数据
            cm = new CourseMarket();
            // 复制数据
            BeanUtils.copyProperties(courseMarket, cm);
            // 设置id
            cm.setId(id);
        } else {
            // 更新数据
            cm.setCharge(courseMarket.getCharge());
            cm.setPrice(courseMarket.getPrice());
            cm.setValid(courseMarket.getValid());
            cm.setStartTime(courseMarket.getStartTime());
            cm.setEndTime(courseMarket.getEndTime());
            cm.setQq(courseMarket.getQq());
        }
        try {
            // 更新数据到数据库
            this.courseMarketRepository.save(cm);
            // 返回更新成功结果
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            throw new CustomException(CourseCode.COURSE_MARKET_UPDATE_ERROR);
        }
    }

    /**
     * 添加课程图片
     *
     * @param courseId
     * @param pic
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addCoursePic(String courseId, String pic) {
        CoursePic coursePic;
        // 需保证每门课程只有一张图片
        Optional<CoursePic> optional = this.coursePicRepository.findById(courseId);
        // 判断是更新还是新建
        coursePic = optional.orElseGet(CoursePic::new);
        // 封装数据
        coursePic.setCourseid(courseId);
        coursePic.setPic(pic);
        // 调动dao进行保存
        this.coursePicRepository.save(coursePic);
        // 返回保存成功结果
        return ResponseResult.SUCCESS();
    }

    /**
     * 查询课程图片
     *
     * @param courseId
     * @return
     */
    @Override
    public CoursePic findCoursePic(String courseId) {
        // 调用dao层进行查询
        Optional<CoursePic> optional = this.coursePicRepository.findById(courseId);
        // 返回查询结果
        return optional.orElse(null);
    }

    /**
     * 删除课程图片
     *
     * @param courseId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteCoursePic(String courseId) {
        // 执行删除，返回1表示删除成功，返回0表示删除失败
        long result = this.coursePicRepository.deleteByCourseid(courseId);
        // 校验删除结果
        if (result > 0) {
            return ResponseResult.SUCCESS();
        }
        return ResponseResult.FAIL();
    }

    /**
     * 获取根结点，如果是新课，那么根结点不存在，需要创建根结点
     *
     * @param courseid
     * @return
     */
    public String getTeachplanRoot(String courseid) {
        // 根据courseid和parentid获取根结点
        List<Teachplan> teachplanList = this.teachplanRepository.findByCourseidAndParentid(courseid, CourseConstant.TEACHPLAN_ROOT_PARENTID);
        // 判断，如果不存在，则为新课，需添加根结点
        if (CollectionUtils.isEmpty(teachplanList)) {
            // 查询课程基本信息
            Optional<CourseBase> optional = this.courseBaseRepository.findById(courseid);
            // 校验课程基本信息
            if (!optional.isPresent()) {
                return null;
            }
            CourseBase courseBase = optional.get();
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