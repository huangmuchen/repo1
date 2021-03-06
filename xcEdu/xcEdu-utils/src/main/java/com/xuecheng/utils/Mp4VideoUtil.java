package com.xuecheng.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 完成avi转mp4
 */
public class Mp4VideoUtil extends VideoUtil {
    private String ffmpeg_path = "D:/ffmpeg/bin/ffmpeg.exe";
    private String video_path = "D:/data/xcEdu/ffmpeg/video/lucene.avi";
    private String mp4_name = "lucene.mp4";
    private String mp4folder_path = "D:/data/xcEdu/ffmpeg/video/";

    public Mp4VideoUtil(String ffmpeg_path, String video_path, String mp4_name, String mp4folder_path) {
        super(ffmpeg_path);
        this.ffmpeg_path = ffmpeg_path;
        this.video_path = video_path;
        this.mp4_name = mp4_name;
        this.mp4folder_path = mp4folder_path;
    }

    /**
     * 清除已生成的mp4
     *
     * @param mp4_path
     */
    private void clear_mp4(String mp4_path) {
        File mp4File = new File(mp4_path);
        if (mp4File.exists() && mp4File.isFile()) {
            mp4File.delete();
        }
    }

    /**
     * 视频编码，生成mp4文件
     *
     * @return 成功返回success，失败返回控制台日志
     */
    public String generateMp4() {
        // 清除已生成的mp4
        clear_mp4(mp4folder_path + mp4_name);
        List<String> commend = new ArrayList<String>();
        commend.add(ffmpeg_path);
        commend.add("-i");
        commend.add(video_path);
        commend.add("-c:v");
        commend.add("libx264");
        commend.add("-y"); // 覆盖输出文件
        commend.add("-s");
        commend.add("1280x720");
        commend.add("-pix_fmt");
        commend.add("yuv420p");
        commend.add("-b:a");
        commend.add("63k");
        commend.add("-b:v");
        commend.add("753k");
        commend.add("-r");
        commend.add("18");
        commend.add(mp4folder_path + mp4_name);
        String outstring = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            // 将标准输入流和错误输入流合并，通过标准输入流程读取信息
            builder.redirectErrorStream(true);
            Process p = builder.start();
            outstring = waitFor(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Boolean check_video_time = this.check_video_time(video_path, mp4folder_path + mp4_name);
        if (!check_video_time) {
            return outstring;
        } else {
            return "success";
        }
    }
}