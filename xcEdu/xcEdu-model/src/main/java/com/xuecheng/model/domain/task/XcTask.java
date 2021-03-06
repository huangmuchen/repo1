package com.xuecheng.model.domain.task;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 待处理任务实体类
 */
@Data
@ToString
@Entity
@Table(name = "xc_task")
// @GenericGenerator(name = "jpa-assigned", strategy = "assigned")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcTask implements Serializable {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "delete_time")
    private Date deleteTime;
    @Column(name = "task_type")
    private String taskType;
    @Column(name = "mq_exchange")
    private String mqExchange;
    @Column(name = "mq_routingkey")
    private String mqRoutingkey;
    @Column(name = "request_body")
    private String requestBody;
    private Integer version;
    private String status;
    private String errormsg;
}