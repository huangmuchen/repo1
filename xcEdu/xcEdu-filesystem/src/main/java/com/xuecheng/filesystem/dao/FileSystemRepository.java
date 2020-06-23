package com.xuecheng.filesystem.dao;

import com.xuecheng.model.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author: HuangMuChen
 * @date: 2020/6/23 11:28
 * @version: V1.0
 * @Description: 文件系统Repository：主要想mogodb中存储文件系统中的文件路径
 */
public interface FileSystemRepository extends MongoRepository<FileSystem, String> {

}