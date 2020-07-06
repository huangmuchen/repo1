package com.xuecheng.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 完成mp4转hls
 */
public class HlsVideoUtil extends VideoUtil {
    private String ffmpeg_path = "D:/ffmpeg/bin/ffmpeg.exe";
    private String video_path = "D:/data/xcEdu/ffmpeg/video/lucene.mp4";
    private String m3u8_name = "lucene.m3u8";
    private String m3u8folder_path = "D:/data/xcEdu/ffmpeg/video/hls/";

    public HlsVideoUtil(String ffmpeg_path, String video_path, String m3u8_name, String m3u8folder_path) {
        super(ffmpeg_path);
        this.ffmpeg_path = ffmpeg_path;
        this.video_path = video_path;
        this.m3u8_name = m3u8_name;
        this.m3u8folder_path = m3u8folder_path;
    }

    /**
     * 删除原来已经生成的m3u8及ts文件
     *
     * @param m3u8_path
     */
    private void clear_m3u8(String m3u8_path) {
        File m3u8dir = new File(m3u8_path);
        if (!m3u8dir.exists()) {
            m3u8dir.mkdirs();
        }
    }

    /**
     * 生成m3u8文件
     *
     * @return 成功则返回success，失败返回控制台日志
     */
    public String generateM3u8() {
        // 清理m3u8文件目录
        clear_m3u8(m3u8folder_path);
        List<String> commend = new ArrayList<>();
        commend.add(ffmpeg_path);
        commend.add("-i");
        commend.add(video_path);
        commend.add("-hls_time");
        commend.add("10");
        commend.add("-hls_list_size");
        commend.add("0");
        commend.add("-hls_segment_filename");
        commend.add(m3u8folder_path + m3u8_name.substring(0, m3u8_name.lastIndexOf(".")) + "_%05d.ts");
        commend.add(m3u8folder_path + m3u8_name);
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
        // 通过查看视频时长判断是否成功
        Boolean check_video_time = check_video_time(video_path, m3u8folder_path + m3u8_name);
        if (!check_video_time) {
            return outstring;
        }
        // 通过查看m3u8列表判断是否成功
        List<String> ts_list = get_ts_list();
        if (ts_list == null) {
            return outstring;
        }
        return "success";
    }

    /**
     * 检查视频处理是否完成
     *
     * @return ts列表
     */
    public List<String> get_ts_list() {
        List<String> tsList = new ArrayList<>();
        String m3u8file_path = m3u8folder_path + m3u8_name;
        BufferedReader br = null;
        String str;
        String bottomline = "";
        try {
            br = new BufferedReader(new FileReader(m3u8file_path));
            while ((str = br.readLine()) != null) {
                bottomline = str;
                if (bottomline.endsWith(".ts")) {
                    tsList.add(bottomline);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (bottomline.contains("#EXT-X-ENDLIST")) {
            return new ArrayList<>(tsList);
        }
        return null;
    }
}