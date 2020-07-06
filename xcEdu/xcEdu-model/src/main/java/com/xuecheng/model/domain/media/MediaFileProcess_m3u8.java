package com.xuecheng.model.domain.media;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2019/9/18 11:32
 * @version: V1.0
 * @Description: ts列表
 */
@Data
@ToString
public class MediaFileProcess_m3u8 extends MediaFileProcess {
    private List<String> tslist; // ts列表
}