package com.xuecheng.model.domain.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: 企业与用户关联实体类
 */
@Data
@ToString
@Entity
@Table(name = "xc_company_user")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class XcCompanyUser implements Serializable {
    private static final long serialVersionUID = -916357110051689786L;

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name = "company_id")
    private String companyId;
    @Column(name = "user_id")
    private String userId;
}