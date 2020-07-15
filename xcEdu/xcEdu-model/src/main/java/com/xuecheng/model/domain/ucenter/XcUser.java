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
 * @Description: 用户实体类，存储了系统用户信息
 */
@Data
@ToString
@Entity
@Table(name = "xc_user")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcUser {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    private String username;
    private String password;
    private String salt;
    private String name;
    private String utype; // 用户类型包括：学生、老师、管理员等
    private String birthday;
    private String userpic;
    private String sex;
    private String email;
    private String phone;
    private String status;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}