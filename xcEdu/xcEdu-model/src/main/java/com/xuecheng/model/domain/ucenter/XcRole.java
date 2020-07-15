package com.xuecheng.model.domain.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 角色实体类，存储了系统的角色信息，学生、老师、教学管理员、系统管理员等
 */
@Data
@ToString
@Entity
@Table(name = "xc_role")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcRole {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name = "role_name") // 角色名称
    private String roleName;
    @Column(name = "role_code") // 角色代号
    private String roleCode;
    private String description; // 描述
    private String status;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}