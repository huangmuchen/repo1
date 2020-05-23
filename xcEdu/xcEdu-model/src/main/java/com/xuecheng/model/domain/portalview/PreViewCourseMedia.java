package com.xuecheng.model.domain.portalview;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
@Document(collection = "pre_view_course_media")
public class PreViewCourseMedia implements Serializable {
    @Id
    @Column(name = "teachplan_id")
    private String teachplanId;
    @Column(name = "media_id")
    private String mediaId;
    @Column(name = "media_fileoriginalname")
    private String mediaFileOriginalName;
    @Column(name = "media_url")
    private String mediaUrl;
    private String courseId;
}