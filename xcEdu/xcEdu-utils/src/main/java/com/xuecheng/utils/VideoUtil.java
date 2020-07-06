package com.xuecheng.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 此文件作为视频文件处理父类，提供：
 * 1、查看视频时长
 * 2、校验两个视频的时长是否相等
 */
public class VideoUtil {
    String ffmpeg_path = "D:/ffmpeg/bin/ffmpeg.exe";

    public VideoUtil(String ffmpeg_path) {
        this.ffmpeg_path = ffmpeg_path;
    }

    // 检查视频时间是否一致
    public Boolean check_video_time(String source, String target) {
        // 获取源视频时间
        String source_time = get_video_time(source);
        // 取出时分秒
        source_time = source_time.substring(0, source_time.lastIndexOf("."));
        // 获取目标视频时间
        String target_time = get_video_time(target);
        // 取出时分秒
        target_time = target_time.substring(0, target_time.lastIndexOf("."));
        // 判断
        if (source_time == null || target_time == null) {
            return false;
        }
        return source_time.equals(target_time);
    }

    /**
     * 获取视频时间(时：分：秒：毫秒)
     *
     * @param video_path
     * @return
     */
    public String get_video_time(String video_path) {
        // 定义命令内容
        List<String> command = new ArrayList<String>();
        command.add(ffmpeg_path);
        command.add("-i");
        command.add(video_path);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            // 封装要执行的命令
            builder.command(command);
            // 将标准输入流和错误输入流合并，通过标准输入流程读取信息
            builder.redirectErrorStream(true);
            // 启动进程
            Process p = builder.start();
            String outstring = waitFor(p);
            int start = outstring.trim().indexOf("Duration: ");
            if (start >= 0) {
                int end = outstring.trim().indexOf(", start:");
                if (end >= 0) {
                    String time = outstring.substring(start + 10, end);
                    if (time != null && !time.equals("")) {
                        return time.trim();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String waitFor(Process p) {
        InputStream in = null;
        InputStream error;
        int exitValue = -1;
        StringBuilder sb = new StringBuilder();
        try {
            in = p.getInputStream();
            error = p.getErrorStream();
            boolean finished = false;
            int maxRetry = 600; // 每次休眠1秒，最长执行时间10分种
            int retry = 0;
            while (!finished) {
                if (retry > maxRetry) {
                    return "error";
                }
                try {
                    while (in.available() > 0) {
                        Character c = (char) in.read();
                        sb.append(c);
                    }
                    while (error.available() > 0) {
                        Character c = (char) in.read();
                        sb.append(c);
                    }
                    // 进程未结束时调用exitValue将抛出异常
                    exitValue = p.exitValue();
                    finished = true;
                } catch (IllegalThreadStateException e) {
                    Thread.sleep(1000); // 休眠1秒
                    retry++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return sb.toString();
    }
}