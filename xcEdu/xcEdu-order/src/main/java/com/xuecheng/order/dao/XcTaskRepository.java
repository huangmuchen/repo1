package com.xuecheng.order.dao;

import com.github.pagehelper.Page;
import com.xuecheng.model.domain.task.XcTask;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * @author: HuangMuChen
 * @date: 2020/7/17 8:14
 * @version: V1.0
 * @Description: 待处理任务Repository
 */
public interface XcTaskRepository extends JpaRepository<XcTask, String> {

    /**
     * 分页查询某个时间之前的N条记录
     *
     * @param pageable
     * @param updateTime
     * @return
     */
    Page<XcTask> findByUpdateTimeBefore(Pageable pageable, Date updateTime);

    /**
     * 更新任务
     *
     * @param updateTime
     * @param id
     * @return
     */
    @Modifying
    @Query("update XcTask t set t.updateTime = :updateTime where t.id = :id")
    int updateTaskTime(@Param("updateTime") Date updateTime, @Param("id") String id);

    /**
     * 乐观锁控制：使用乐观锁方式校验任务id和版本号是否匹配，匹配则版本号加1
     *
     * @param id
     * @param version
     * @return 更新成功返回1，否则返回0
     */
    @Modifying
    @Query("update XcTask t set t.version = :version+1 where t.id = :id and t.version = :version")
    public int updateTaskVersion(@Param(value = "id") String id, @Param(value = "version") int version);
}