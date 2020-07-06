package com.xuecheng.media_processor.service.impl;

import com.xuecheng.common.constant.MediaConstant;
import com.xuecheng.common.exception.CustomException;
import com.xuecheng.media_processor.config.MediaProcessorProperties;
import com.xuecheng.media_processor.dao.MediaFileRepository;
import com.xuecheng.media_processor.service.IMediaProcessService;
import com.xuecheng.model.domain.media.MediaFile;
import com.xuecheng.model.domain.media.MediaFileProcess_m3u8;
import com.xuecheng.model.domain.media.response.MediaCode;
import com.xuecheng.utils.HlsVideoUtil;
import com.xuecheng.utils.Mp4VideoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author: HuangMuChen
 * @date: 2020/7/5 0:40
 * @version: V1.0
 * @Description: 媒资视频处理业务层实现类
 */
@Service
@Slf4j
@EnableConfigurationProperties(MediaProcessorProperties.class)
public class MediaProcessServiceImpl implements IMediaProcessService {
    @Autowired
    private MediaFileRepository mediaFileRepository;
    @Autowired
    private MediaProcessorProperties prop;

    /**
     * 根据mediaId处理视频
     *
     * @param mediaId
     */
    @Override
    public void processMedia(String mediaId) {
        // 根据mediaId查询媒资文件信息
        Optional<MediaFile> optional = this.mediaFileRepository.findById(mediaId);
        // 校验媒资文件
        if (!optional.isPresent()) {
            // 记录错误日志
            log.error("media processing failed，the reason is mediaId illegal，mediaId ：{}", mediaId);
            throw new CustomException(MediaCode.MEDIA_FILE_NOTEXIST);
        }
        // 获取媒资文件信息
        MediaFile mediaFile = optional.get();
        // 校验视频类型，当前只有flv文件需要处理，其它文件需要更新处理状态为“无需处理”。
        if (StringUtils.isBlank(mediaFile.getFileType()) || !mediaFile.getFileType().equalsIgnoreCase("flv")) {
            // 更新处理状态为：无需处理
            mediaFile.setProcessStatus(MediaConstant.MEDIAPROCESS_STATUS_PROCESSED);
            // 更新处理状态
            this.mediaFileRepository.save(mediaFile);
            // 直接返回
            return;
        } else {
            // 更新处理状态为：未处理
            mediaFile.setProcessStatus(MediaConstant.MEDIAPROCESS_STATUS_UNPROCESS);
            // 更新处理状态
            this.mediaFileRepository.save(mediaFile);
        }
        // 根据视频路径使用工具类将flv生成mp4文件
        Boolean toMp4Flag = toMp4(mediaFile);
        if (!toMp4Flag) {
            return;
        }
        // 将mp4生成m3u8和ts文件
        toM3u8(mediaFile);
    }

    /**
     * 生成mp4文件
     *
     * @param mediaFile
     * @return
     */
    public Boolean toMp4(MediaFile mediaFile) {
        // 获取视频文件绝对路径
        String video_path = prop.getLocation() + mediaFile.getFilePath() + mediaFile.getFileName();
        // 定义生成的mp4文件名称
        String mp4_name = mediaFile.getFileId() + ".mp4";
        // 定义存储mp4文件路径
        String mp4folder_path = prop.getLocation() + mediaFile.getFilePath();
        // ffmpeg的安装位置
        String ffmpeg_path = prop.getFfmpegPath();
        // 创建mp4工具类对象
        Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(ffmpeg_path, video_path, mp4_name, mp4folder_path);
        // 将flv生成mp4文件
        String mp4Result = mp4VideoUtil.generateMp4();
        // 判断是否成功
        if (StringUtils.isBlank(mp4Result) || !mp4Result.equals("success")) {
            // 向mogodb数据库中记录错误信息，并更新处理状态为：处理失败
            mediaFile.setProcessStatus(MediaConstant.MEDIAPROCESS_STATUS_FAIL);
            MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
            mediaFileProcess_m3u8.setErrormsg(mp4Result);
            mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
            // 调用dao保存数据
            this.mediaFileRepository.save(mediaFile);
            // 记录错误日志
            log.error("media processing failed，the reason is avi to mp4 fail，fail mediaId：{}，fail reason ：{}", mediaFile.getFileId(), mp4Result);
            // 返回处理失败
            return false;
        }
        // 返回处理成功
        return true;
    }

    /**
     * mp4转m3u8
     *
     * @param mediaFile
     */
    public void toM3u8(MediaFile mediaFile) {
        // 获取mp4文件路径
        String video_path = prop.getLocation() + mediaFile.getFilePath() + mediaFile.getFileId() + ".mp4";
        // 定义生成的m3u8名称
        String m3u8_name = mediaFile.getFileId() + ".m3u8";
        // 定义 m3u8文件夹路径
        String m3u8folder_path = prop.getLocation() + mediaFile.getFilePath() + "hls/";
        // ffmpeg的安装位置
        String ffmpeg_path = prop.getFfmpegPath();
        // 创建hls工具类对象
        HlsVideoUtil hlsVideoUtil = new HlsVideoUtil(ffmpeg_path, video_path, m3u8_name, m3u8folder_path);
        // mp4转m3u8
        String m3u8Result = hlsVideoUtil.generateM3u8();
        MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
        // 判断是否成功
        if (StringUtils.isBlank(m3u8Result) || !m3u8Result.equals("success")) {
            // 向mogodb数据库中记录错误信息，并更新处理状态为：处理失败
            mediaFile.setProcessStatus(MediaConstant.MEDIAPROCESS_STATUS_FAIL);
            mediaFileProcess_m3u8.setErrormsg(m3u8Result);
            mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
            // 调用dao保存数据
            this.mediaFileRepository.save(mediaFile);
            // 记录错误日志
            log.error("media processing failed，the reason is mp4 to m3u8 fail，fail mediaId：{}，fail reason ：{}", mediaFile.getFileId(), m3u8Result);
            // 返回处理失败
            return;
        }
        // 获取m3u8文件列表
        List<String> ts_list = hlsVideoUtil.get_ts_list();
        mediaFileProcess_m3u8.setTslist(ts_list);
        mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
        // 更新处理状态为：处理成功
        mediaFile.setProcessStatus(MediaConstant.MEDIAPROCESS_STATUS_SUCCESS);
        // 记录m3u8文件相对路径
        mediaFile.setFileUrl(mediaFile.getFilePath() + "hls/" + m3u8_name);
        // 调用dao保存数据
        this.mediaFileRepository.save(mediaFile);
    }
}