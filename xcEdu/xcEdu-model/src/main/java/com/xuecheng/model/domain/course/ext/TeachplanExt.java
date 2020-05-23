package com.xuecheng.model.domain.course.ext;

import com.xuecheng.model.domain.course.Teachplan;
import lombok.Data;
import lombok.ToString;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: TODO
 */
@Data
@ToString
public class TeachplanExt extends Teachplan {
    private String mediaId; // 媒资文件id
    private String mediaFileOriginalName; // 媒资文件原始名称
    private String mediaUrl; // 媒资文件访问地址
}