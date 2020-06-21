package com.xuecheng.course.dao.repository;

import com.xuecheng.model.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: HuangMuChen
 * @date: 2020/6/21 21:00
 * @version: V1.0
 * @Description: 课程计划Repository
 */
public interface TeachplanRepository extends JpaRepository<Teachplan, String> {

    /**
     * 根据课程id和父结点id查询出结点列表，可以使用此方法查询根结点(parentid = '0')
     *
     * @param courseid
     * @param parentid
     * @return
     */
    List<Teachplan> findByCourseidAndParentid(String courseid, String parentid);
}