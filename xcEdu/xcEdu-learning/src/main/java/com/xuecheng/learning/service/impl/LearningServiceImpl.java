package com.xuecheng.learning.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.common.exception.CustomException;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.QueryResponseResult;
import com.xuecheng.common.model.response.QueryResult;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.learning.client.CourseSearchClient;
import com.xuecheng.learning.config.LearningProperties;
import com.xuecheng.learning.dao.mapper.LearningCourseMapper;
import com.xuecheng.learning.dao.repository.XcLearningCourseRepository;
import com.xuecheng.learning.dao.repository.XcTaskHisRepository;
import com.xuecheng.learning.service.ILearningService;
import com.xuecheng.model.domain.course.CourseBase;
import com.xuecheng.model.domain.course.TeachplanMediaPub;
import com.xuecheng.model.domain.course.request.CourseListRequest;
import com.xuecheng.model.domain.learning.XcLearningCourse;
import com.xuecheng.model.domain.learning.response.GetMediaResult;
import com.xuecheng.model.domain.learning.response.LearningCode;
import com.xuecheng.model.domain.task.XcTask;
import com.xuecheng.model.domain.task.XcTaskHis;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author: HuangMuChen
 * @date: 2020/7/9 21:28
 * @version: V1.0
 * @Description: 课程学习业务层实现类
 */
@Service
@Slf4j
@EnableConfigurationProperties(LearningProperties.class)
public class LearningServiceImpl implements ILearningService, RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    @Autowired
    private CourseSearchClient courseSearchClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private XcLearningCourseRepository learningCourseRepository;
    @Autowired
    private XcTaskHisRepository taskHisRepository;
    @Autowired
    private LearningProperties prop;
    @Autowired
    private LearningCourseMapper learningCourseMapper;

    /**
     * 根据用户id查询用户已选课程
     *
     * @param page
     * @param size
     * @param courseListRequest
     * @param userId
     * @return
     */
    @Override
    public QueryResponseResult chooseCourseList(int page, int size, String userId, CourseListRequest courseListRequest) {
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
            // 设置companyId
            courseListRequest.setUserId(userId);
            // 设置分页
            PageHelper.startPage(page, size);
            // 调用mapper层进行查询
            Page<XcLearningCourse> pageList = this.learningCourseMapper.chooseCourseList(courseListRequest);
            // 构建返回结果对象,并封装查询结果
            QueryResult<XcLearningCourse> queryResult = new QueryResult<>();
            // 设置课程列表
            queryResult.setList(pageList.getResult());
            // 设置总条数
            queryResult.setTotal(pageList.getTotal());
            // 返回查询成功结果
            return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
        } catch (Exception e) {
            e.printStackTrace();
            // 返回查询失败结果
            return new QueryResponseResult<>(CommonCode.FAIL, new QueryResult<CourseBase>());
        }
    }

    /**
     * 查询录播课程学习地址
     *
     * @param courseId
     * @param teachplanId
     * @return
     */
    @Override
    public GetMediaResult getMedia(String courseId, String teachplanId) {
        // TODO：校验学生的学习权限...是否资费等

        // 通过Feign远程调用Search服务
        TeachplanMediaPub teachplanMediaPub = this.courseSearchClient.getMedia(teachplanId);
        // 校验
        if (teachplanMediaPub == null || StringUtils.isEmpty(teachplanMediaPub.getMediaUrl())) {
            // 获取视频播放地址出错
            throw new CustomException(LearningCode.LEARNING_GETMEDIA_FAIL);
        }
        // 返回查询结果
        return new GetMediaResult(CommonCode.SUCCESS, teachplanMediaPub.getMediaUrl());
    }

    /**
     * 自动添加课程
     *
     * @param userId
     * @param courseId
     * @param valid
     * @param startTime
     * @param endTime
     * @param xcTask
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addcourse(String userId, String courseId, String valid, Date startTime, Date endTime, XcTask xcTask) {
        // 参数判断
        if (xcTask == null || StringUtils.isBlank(xcTask.getId())) {
            throw new CustomException(LearningCode.CHOOSECOURSE_TASKISNULL);
        }
        if (StringUtils.isBlank(courseId)) {
            throw new CustomException(LearningCode.LEARNING_GETMEDIA_ERROR);
        }
        if (StringUtils.isBlank(userId)) {
            throw new CustomException(LearningCode.CHOOSECOURSE_USERISNULL);
        }
        // 查询是否有选课记录
        XcLearningCourse learningCourse = this.learningCourseRepository.findByCourseIdAndUserId(courseId, userId);
        // 幂等判断，如果有则修改，没有新增
        if (learningCourse != null) {
            learningCourse.setStartTime(startTime);
            learningCourse.setEndTime(endTime);
            learningCourse.setStatus("501001"); // 选课状态
            this.learningCourseRepository.save(learningCourse);
        } else {
            learningCourse = new XcLearningCourse();
            learningCourse.setUserId(userId);
            learningCourse.setCourseId(courseId);
            learningCourse.setValid(valid);
            learningCourse.setStartTime(startTime);
            learningCourse.setEndTime(endTime);
            learningCourse.setStatus("501001");
            this.learningCourseRepository.save(learningCourse);
        }
        // 查询xc_task_his表中是否有记录
        Optional<XcTaskHis> optional = this.taskHisRepository.findById(xcTask.getId());
        // 是否插入历史任务表，如果不存在，添加，如果存在就不操作了
        if (!optional.isPresent()) {
            // 创新历史任务表对象
            XcTaskHis xcTaskHis = new XcTaskHis();
            // 属性复制
            BeanUtils.copyProperties(xcTask, xcTaskHis);
            // 保存到数据库
            this.taskHisRepository.save(xcTaskHis);
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 发送选课添加成功消息给mq
     *
     * @param task
     */
    @Override
    public void sendFinishChooseCourseMsg(XcTask task) {
        // 开启强制委托模式
        this.rabbitTemplate.setMandatory(true);
        // 设置消息发送给交换机的回调
        this.rabbitTemplate.setConfirmCallback(this);
        // 设置消息从交换机发送给队列的回调
        this.rabbitTemplate.setReturnCallback(this);
        // 关联数据
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 发送消息给交换机(如果不指定，将发给.yml文件中默认的交换机))，并将站点id作为routingKey
        this.rabbitTemplate.convertAndSend(prop.getExchange(), prop.getRoutingKey_finish(), task, correlationData);
    }

    /**
     * 消息是否成功到达交换机
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            // 消息成功到达交换机
            log.info("回调消息ID为：{}，消息成功发送到交换机！", correlationData.getId());
        } else {
            // 消息到达交换机失败
            log.error("回调消息ID为：{}，消发送到交换机失败！原因：[{}]", correlationData.getId(), cause);
        }
    }

    /**
     * 消息是否成功从交换机到达队列：成功，则returnedMessage方法不会执行，失败，returnedMessage方法会执行
     *
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("发送消息：{}，错误码：{}，错误原因：{}，exchange：{}，routingKey：{}", new String(message.getBody(), StandardCharsets.UTF_8), replyCode, replyText, exchange, routingKey);
    }
}