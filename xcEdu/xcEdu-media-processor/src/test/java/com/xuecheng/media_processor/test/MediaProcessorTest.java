package com.xuecheng.media_processor.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2020/7/5 20:17
 * @version: V1.0
 * @Description: TODO
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MediaProcessorTest {

    @Test
    public void testProcessBuilder() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        // 封装要执行的命令，比如："ipconfig"
        processBuilder.command("ping", "127.0.0.1");
        // 将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        try {
            // 启动进程
            Process start = processBuilder.start();
            // 获取输入流
            InputStream inputStream = start.getInputStream();
            // 转成字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            int len = -1;
            char[] c = new char[1024];
            StringBuilder sb = new StringBuilder();
            // 读取进程输入流中的内容
            while ((len = inputStreamReader.read(c)) != -1) {
                String s = new String(c, 0, len);
                sb.append(s);
            }
            System.out.println(sb);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFFmpeg() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        // 定义命令内容
        List<String> command = new ArrayList<>();
        command.add("D:/ffmpeg/bin/ffmpeg.exe");
        command.add("-i");
        command.add("D:/data/xcEdu/ffmpeg/video/lucene.avi");
        command.add("-y"); // 覆盖输出文件
        command.add("-c:v");
        command.add("libx264");
        command.add("-s");
        command.add("1280x720");
        command.add("-pix_fmt");
        command.add("yuv420p");
        command.add("-b:a");
        command.add("63k");
        command.add("-b:v");
        command.add("753k");
        command.add("-r");
        command.add("18");
        command.add("D:/data/xcEdu/ffmpeg/video/lucene.mp4");
        // 封装要执行的命令
        processBuilder.command(command);
        // 将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        try {
            // 启动进程
            Process start = processBuilder.start();
            // 获取输入流
            InputStream inputStream = start.getInputStream();
            // 转成字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            int len = -1;
            char[] c = new char[1024];
            StringBuilder sb = new StringBuilder();
            // 读取进程输入流中的内容
            while ((len = inputStreamReader.read(c)) != -1) {
                String s = new String(c, 0, len);
                sb.append(s);
            }
            // 打印外部程序执行结果
            System.out.println(sb);
            // 关闭输入流
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}