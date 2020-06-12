package com.xuecheng.cms.other;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: HuangMuChen
 * @date: 2020/6/11 14:12
 * @version: V1.0
 * @Description: 测试GridFs
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTest {
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;

    /**
     * 存储模板文件到GridFS
     */
    @Test
    public void testGridFs() throws Exception {
        // 要存储的文件
        File file = new File("D:/log/Freemarker/index_banner.ftl");
        // 输入流
        FileInputStream in = new FileInputStream(file);
        // 向GridFS存储文件,返回文件id,此id对应模板信息中templateFileId
        ObjectId objectId = this.gridFsTemplate.store(in, "轮播图测试文件01", "");
        // 打印文件id
        System.out.println(objectId.toString());
        // 关闭资源
        in.close();
    }

    /**
     * 下载GridFs中文件
     *
     * @throws Exception
     */
    @Test
    public void testDownloadFile() throws Exception {
        // 文件id
        String templateFileId = "5e4e80fc392c02477c5df312";
        // 输出流
        FileOutputStream out = new FileOutputStream(new File("D:/log/Freemarker/lunbotu.html"));
        // 下载文件
        this.gridFSBucket.downloadToStream(new ObjectId(templateFileId), out);
        // 关闭资源
        out.close();
    }

    /**
     * 查询GridFs中文件
     *
     * @throws IOException
     */
    @Test
    public void testQueryFile() throws IOException {
        // 模板文件id
        String templateFileId = "5e4e80fc392c02477c5df312";
        // 根据id查询文件
        GridFSFile gridFSFile = this.gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
        // 打开下载流对象
        GridFSDownloadStream gridFSDownloadStream = this.gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        // 创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        // 获取流中的数据
        String ftl = IOUtils.toString(gridFsResource.getInputStream(), StandardCharsets.UTF_8);
        // 打印
        System.out.println(ftl);
    }

    /**
     * 删除GridFs中文件
     *
     * @throws IOException
     */
    @Test
    public void testDelFile() throws IOException {
        // 文件id
        String templateFileId = "5e63a031a6a4ac57c8aa9209";
        // 根据文件id删除fs.files和fs.chunks中的记录
        this.gridFsTemplate.delete(Query.query(Criteria.where("_id").is(templateFileId)));
    }
}
