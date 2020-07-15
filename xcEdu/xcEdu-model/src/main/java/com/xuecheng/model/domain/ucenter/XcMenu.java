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
 * @Description: 菜单实体类（也就是权限表，不同权限可以点击不同菜单），记录了菜单及菜单下的权限
 */
@Data
@ToString
@Entity
@Table(name = "xc_menu")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcMenu {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    private String code;
    @Column(name = "p_id")
    private String pId;
    @Column(name = "menu_name")
    private String menuName;
    private String url;
    @Column(name = "is_menu")
    private String isMenu;
    private Integer level;
    private Integer sort;
    private String status;
    private String icon;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}