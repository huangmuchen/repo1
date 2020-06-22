package com.xuecheng.course.dao.repository;

import com.xuecheng.model.domain.course.CourseMarket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: HuangMuChen
 * @date: 2020/6/18 15:38
 * @version: V1.0
 * @Description: 课程营销Repository
 */
public interface CourseMarketRepository extends JpaRepository<CourseMarket,String> {

}