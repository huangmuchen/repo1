package com.xuecheng.order.service;

import com.xuecheng.model.domain.task.XcTask;

import java.util.Date;
import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2020/7/17 8:35
 * @version: V1.0
 * @Description: 定时任务业务层接口
 */
public interface ITaskService {

    /**
     * 分页查询某个时间之前的N条记录
     *
     * @param size
     * @param time
     * @return
     */
    List<XcTask> getTaskList(int size, Date time);

    /**
     * 发送自动选课消息给mq
     *
     * @param task
     * @param exchange
     * @param routingkey
     */
    void sendChooseCourseMsg(XcTask task, String exchange, String routingkey);

    /**
     * 通过能成功更新任务版本号，进行乐观锁控制
     *
     * @param id
     * @param version
     * @return
     */
    int versionControl(String id, int version);

    /**
     * 删除待处理任务，并将任务移至历史任务表中
     *
     * @param id
     */
    void saveHisAndDeleteTask(String id);
}