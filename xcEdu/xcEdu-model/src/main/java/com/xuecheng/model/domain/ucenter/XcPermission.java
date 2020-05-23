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
 * @Description: TODO
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
    @Column(name = "roleId")
    private String role_id;
    @Column(name = "menuId")
    private String menu_id;
    @Column(name = "createTime")
    private Date create_time;
}