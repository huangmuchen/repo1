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
 * @Description: 用户角色中间表：不同用户拥有不同角色（多对多关系：一个用户可以有多个角色，一个角色可以对应多个用户）
 */
@Data
@ToString
@Entity
@Table(name = "xc_user_role")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcUserRole {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "role_id")
    private String roleId;
    private String creator;
    @Column(name = "create_time")
    private Date createTime;
}