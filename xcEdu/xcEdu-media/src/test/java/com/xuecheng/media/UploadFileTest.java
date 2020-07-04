package com.xuecheng.media;

import com.xuecheng.MediaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2020/7/3 22:17
 * @version: V1.0
 * @Description: 文件上传测试
 * <p>
 * RandomAccessFile：一个可以对文件随机访问的操作流，访问包括读和写操作，该类的读写是基于指针的操作
 */
@SpringBootTest(classes = MediaApplication.class)
@RunWith(SpringRunner.class)
public class UploadFileTest {

    /**
     * 文件分块
     */
    @Test
    public void testChunk() throws IOException {
        // 定义源文件
        File sourceFile = new File("D:/data/xcEdu/ffmpeg/video/lucene.avi");
        // 定义块文件路径
        String chunkFilePath = "D:/data/xcEdu/ffmpeg/video/chunks/";
        // 定义分块大小：1M
        long chunkSize = 1024 * 1024;
        // 计算分块数目：采用向上取整，* 1.0是为了转成浮点数，即把结果转成小数（比如：0.4）,向上取整为1
        long chunkNum = (long) Math.ceil(sourceFile.length() * 1.0 / chunkSize);
        // 创建一个随机读文件流
        RandomAccessFile raf_read = new RandomAccessFile(sourceFile, "r");
        // 读源文件
        for (int i = 0; i < chunkNum; i++) {
            // 定义分块文件
            File chunkFile = new File(chunkFilePath + i);
            // 创建一个随机写文件流
            RandomAccessFile raf_write = new RandomAccessFile(chunkFile, "rw");
            // 定义一个字节缓冲数组
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = raf_read.read(bytes)) != -1) {
                raf_write.write(bytes, 0, len);
                // 判断
                if (chunkFile.length() >= chunkSize) {
                    break;
                }
            }
            // 关闭随机写流
            raf_write.close();
        }
        // 关闭随机读流
        raf_read.close();
    }

    /**
     * 文件合并
     */
    @Test
    public void testMerge() throws IOException {
        // 获取块文件路径
        String chunkFilePath = "D:/data/xcEdu/ffmpeg/video/chunks/";
        // 获取块文件夹
        File chunkFile = new File(chunkFilePath);
        // 获取块文件列表
        File[] chunkFiles = chunkFile.listFiles();
        // 转成List集合
        List<File> fileList = Arrays.asList(chunkFiles);
        // 对集合中的块文件按名称进行升序排序
        fileList.sort(new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Integer.parseInt(o1.getName()) - Integer.parseInt(o2.getName());
            }
        });
        // 合并文件
        File mergeFile = new File("D:/data/xcEdu/ffmpeg/video/lucene_merge.avi");
        // 创建新的合并文件
        mergeFile.createNewFile();
        // 创建随机写文件流
        RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw");
        // 定义一个字节缓冲数组
        byte[] bytes = new byte[1024];
        // 遍历fileList集合，将排好序的块文件写入合并文件中
        for (File file : fileList) {
            // 创建一个随机读文件流
            RandomAccessFile raf_read = new RandomAccessFile(file, "r");
            int len = -1;
            while ((len = raf_read.read(bytes)) != -1) {
                raf_write.write(bytes, 0, len);
            }
            // 每写完一个块文件，就关闭读文件流
            raf_read.close();
        }
        // 全部写完后，关闭写文件流
        raf_write.close();
    }

    /**
     * 测试文件大小，单位：字节
     */
    @Test
    public void testFileSize() {
        File file = new File("D:/data/xcEdu/ffmpeg/video/lucene.avi");
        System.out.println(file.length());
    }

    /**
     * 删除文件及文件目录
     */
    @Test
    public void testDeleteFile() {
        File fileFolder = new File("D:/data/xcEdu/ffmpeg/video/hello/");
        File[] files = fileFolder.listFiles();
        for (File deFile : files) {
            deFile.delete();
        }
        boolean delete = fileFolder.delete();
        System.out.println(delete);
    }
}