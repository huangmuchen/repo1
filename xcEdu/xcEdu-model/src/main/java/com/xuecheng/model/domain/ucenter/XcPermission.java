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
 * @Description: 角色权限表，一个角色可拥有多个权限，一个权限可被多个角色所拥有
 */
@Data
@ToString
@Entity
@Table(name = "xc_permission")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcPermission {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name = "role_id")
    private String roleId;
    @Column(name = "menu_id")
    private String menuId;
    @Column(name = "create_time")
    private Date createTime;
}