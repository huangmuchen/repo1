package com.xuecheng.course.dao;

import com.xuecheng.CourseApplication;
import com.xuecheng.course.dao.mapper.CourseMapper;
import com.xuecheng.course.dao.repository.CourseBaseRepository;
import com.xuecheng.model.domain.course.CourseBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @author: HuangMuChen
 * @date: 2020/6/18 21:31
 * @version: V1.0
 * @Description: 测试类
 */
@SpringBootTest(classes = CourseApplication.class)
@RunWith(SpringRunner.class)
public class DaoTest {
    @Autowired
    private CourseBaseRepository courseBaseRepository;
    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void CourseBaseRepositoryTest() {
        Optional<CourseBase> optional = this.courseBaseRepository.findById("297e7c7c62b888f00162b8a7dec20000");
        if (optional.isPresent()) {
            CourseBase courseBase = optional.get();
            System.out.println(courseBase);
        }
    }

    @Test
    public void CourseMapperTest() {
        CourseBase courseBase = this.courseMapper.findCourseBaseById("4028858162e0bc0a0162e0bfdf1a0000");
        System.out.println(courseBase);
    }
}