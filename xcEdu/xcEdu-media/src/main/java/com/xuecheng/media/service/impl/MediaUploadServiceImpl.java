package com.xuecheng.media.service.impl;

import com.xuecheng.common.constant.MediaConstant;
import com.xuecheng.common.exception.CustomException;
import com.xuecheng.common.model.response.CommonCode;
import com.xuecheng.common.model.response.ResponseResult;
import com.xuecheng.media.config.MediaUploadProperties;
import com.xuecheng.media.dao.MediaFileRepository;
import com.xuecheng.media.service.IMediaUploadService;
import com.xuecheng.model.domain.media.MediaFile;
import com.xuecheng.model.domain.media.response.CheckChunkResult;
import com.xuecheng.model.domain.media.response.MediaCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * @author: HuangMuChen
 * @date: 2020/7/3 15:57
 * @version: V1.0
 * @Description: 媒资文件上传业务层实现类
 */
@Service
@Slf4j
@EnableConfigurationProperties(MediaUploadProperties.class)
public class MediaUploadServiceImpl implements IMediaUploadService {
    @Autowired
    private MediaFileRepository mediaFileRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MediaUploadProperties prop;

    /**
     * 文件上传前进行一系列文件检查及文件夹创建
     *
     * @param fileMd5
     * @param fileName
     * @param fileSize
     * @param mimetype
     * @param fileExt
     * @return
     */
    @Override
    public ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        try {
            // 检查文件是否上传，已上传则直接返回
            File file = new File(getFilePath(fileMd5, fileExt));
            // 查询mogodb数据库中是否存在文件
            Optional<MediaFile> optional = this.mediaFileRepository.findById(fileMd5);
            // 校验文件是否存在
            if (optional.isPresent() && file.exists()) {
                return new ResponseResult(MediaCode.UPLOAD_FILE_REGISTER_EXIST);
            }
            // 检查文件上传路径是否存在，不存在则创建
            File fileFolder = new File(prop.getLocation() + "/" + fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/");
            if (!fileFolder.exists()) {
                // 文件夹不存在才创建
                fileFolder.mkdirs();
            }
            // 响应注册成功
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            log.error("上传文件注册失败：{}", e.getMessage());
            // 响应注册失败
            return new ResponseResult(MediaCode.UPLOAD_FILE_REGISTER_FAIL);
        }
    }

    /**
     * 检查分块是否存在，如果已存在，则不再上传，达到断点续传的目的
     *
     * @param fileMd5
     * @param chunk
     * @param chunkSize
     * @return
     */
    @Override
    public CheckChunkResult checkchunk(String fileMd5, Integer chunk, Integer chunkSize) {
        // 获取分块文件目录
        String chunkFolderPath = getChunkFolderPath(fileMd5);
        // 块文件的文件名称以1,2,3..序号命名，没有扩展名
        File chunkFile = new File(chunkFolderPath + chunk);
        // 校验分块文件
        if (chunkFile.exists() && chunkFile.length() == chunkSize) {
            // 存在且完整
            return new CheckChunkResult(MediaCode.CHUNK_FILE_EXIST_CHECK, true);
        } else {
            // 不存在或不完整
            return new CheckChunkResult(CommonCode.SUCCESS, false);
        }
    }

    /**
     * 上传分块
     *
     * @param file
     * @param chunk
     * @param fileMd5
     * @return
     */
    @Override
    public ResponseResult uploadchunk(MultipartFile file, Integer chunk, String fileMd5) {
        // 获取分块文件目录
        String chunkFolderPath = getChunkFolderPath(fileMd5);
        // 检查分块目录是否存在
        File chunkFolder = new File(chunkFolderPath);
        // 判断
        if (!chunkFolder.exists()) {
            // 不存在，则创建
            chunkFolder.mkdirs();
        }
        // 定义输出流
        FileOutputStream out = null;
        // 定义输入流
        InputStream in = null;
        try {
            // 获取输出流
            out = new FileOutputStream(new File(chunkFolderPath + chunk));
            // 获取输入流
            in = file.getInputStream();
            // 进行文件上传
            IOUtils.copy(in, out);
        } catch (Exception e) {
            log.error("上传块文件失败：{}", e.getMessage());
            throw new CustomException(MediaCode.CHUNK_FILE_UPLOAD_FAIL);
        } finally {
            try {
                // 关闭输入流
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                // 关闭输出流
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 响应上传成功
        return ResponseResult.SUCCESS();
    }

    /**
     * 文件块全部上传完毕后，进行合并，并删除之前的文件块
     *
     * @param fileMd5
     * @param fileName
     * @param fileSize
     * @param mimetype
     * @param fileExt
     * @return
     */
    @Override
    public ResponseResult mergechunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        try {
            // 获取分块文件目录
            String chunkFolderPath = getChunkFolderPath(fileMd5);
            // 检查分块目录是否存在
            File chunkFolder = new File(chunkFolderPath);
            // 获取排好序的块文件列表
            List<File> chunkFileList = getSortChunkFiles(chunkFolder);
            // 合并文件路径
            File mergeFile = new File(getFilePath(fileMd5, fileExt));
            // 判断合并文件是否存在，如果存在，则先删除，再创建合并文件
            if (mergeFile.exists()) {
                mergeFile.delete();
            }else {
                mergeFile.createNewFile();
            }
            // 开始合并文件
            mergeFile = mergeFile(mergeFile, chunkFileList);
            // 校验文件md5是否正确
            boolean checkResult = checkFileMd5(mergeFile, fileMd5);
            if (!checkResult) {
                throw new CustomException(MediaCode.MERGE_FILE_CHECKFAIL);
            }
            // 创建媒资文件对象
            MediaFile mediaFile = new MediaFile();
            // 填充数据
            mediaFile.setFileId(fileMd5);
            mediaFile.setFileName(fileMd5 + "." + fileExt);
            mediaFile.setFileOriginalName(fileName);
            mediaFile.setFileSize(fileSize);
            mediaFile.setFileType(mimetype);
            mediaFile.setFileType(fileExt);
            mediaFile.setUploadTime(new Date());
            mediaFile.setFileStatus(MediaConstant.MEDIAFILE_STATUS_SUCCESS);
            mediaFile.setFilePath(fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/");
            // 写入mogodb
            this.mediaFileRepository.save(mediaFile);
            // TODO：上传完毕，需要发送消息给MQ，进行消息处理

            // 删除Chunk
            deleteChunk(fileMd5);
            // 响应合并成功
            return new ResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("合并文件失败：{}", e.getMessage());
            throw new CustomException(MediaCode.MERGE_FILE_FAIL);
        }
    }

    /**
     * 校验文件md5是否正确
     *
     * @param mergeFile
     * @param fileMd5
     * @return
     */
    private boolean checkFileMd5(File mergeFile, String fileMd5) {
        if (mergeFile == null || StringUtils.isBlank(fileMd5)) {
            return false;
        }
        // 定义文件输入流
        FileInputStream in = null;
        try {
            // 获取输出流
            in = new FileInputStream(mergeFile);
            // 得到文件的md5
            String md5Hex = DigestUtils.md5Hex(in);
            // 进行md5校验
            return fileMd5.equalsIgnoreCase(md5Hex);
        } catch (Exception e) {
            log.error("校验文件md5异常，file is：{},md5 is：{}", mergeFile.getAbsoluteFile(), fileMd5);
            return false;
        } finally {
            try {
                // 关闭输入流
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 合并文件
     *
     * @param mergeFile
     * @param chunkFileList
     * @return
     */
    private File mergeFile(File mergeFile, List<File> chunkFileList) {
        try {
            // 创建随机写文件流
            RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw");
            // 定义一个字节缓冲数组
            byte[] bytes = new byte[1024];
            // 遍历chunkFileList集合，将排好序的块文件写入合并文件中
            for (File file : chunkFileList) {
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
            // 返回合并后的文件
            return mergeFile;
        } catch (Exception e) {
            log.error("合并文件失败：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 对文件夹中文件进行升序排序，并转成集合
     *
     * @param chunkFolder
     * @return
     */
    private List<File> getSortChunkFiles(File chunkFolder) {
        // 将数组转成集合
        List<File> fileList = Arrays.asList(chunkFolder.listFiles());
        // 对集合中的块文件按名称进行升序排序
        fileList.sort(new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Integer.parseInt(o1.getName()) - Integer.parseInt(o2.getName());
            }
        });
        return fileList;
    }

    /**
     * 删除Chunk
     *
     * @param fileMd5
     */
    private void deleteChunk(String fileMd5) {
        // 获取分块目录
        File chunkFolder = new File(getChunkFolderPath(fileMd5));
        // 获取分块文件列表
        File[] files = chunkFolder.listFiles();
        // 遍历分块文件
        for (File file : files) {
            // 删除分块文件
            file.delete();
        }
        // 删除分块目录
        chunkFolder.delete();
    }

    /**
     * 根据文件md5得到文件路径：一级目录：md5的第一个字符，二级目录：md5的第二个字符，三级目录：md5，文件名：md5+文件扩展名
     *
     * @param fileMd5 文件md5值：4207a61ed6b5716926469289bfffa1b3
     * @param fileExt 文件扩展名：flv
     * @return 文件路径
     */
    private String getFilePath(String fileMd5, String fileExt) {
        return prop.getLocation() + fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/" + fileMd5 + "." + fileExt;
    }

    /**
     * 获取分块文件所在目录
     *
     * @param fileMd5
     * @return
     */
    private String getChunkFolderPath(String fileMd5) {
        return prop.getLocation() + fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/chunk/";
    }
}