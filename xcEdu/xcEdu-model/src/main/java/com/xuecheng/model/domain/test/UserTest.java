package com.xuecheng.model.domain.test;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
@Document(collection = "user_test")
public class UserTest {
    @Id
    private String id;
    private String name;
    @Column(name = "create_time")
    private Date createTime;
}