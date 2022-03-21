package com.jiang.eduservice.service;

import com.jiang.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.eduservice.entity.vo.CourseInfoVo;
import com.jiang.eduservice.entity.vo.CoursePublishVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Shawn
 * @since 2022-03-01
 */
public interface EduCourseService extends IService<EduCourse> {
    //添加课程基本信息的方法
    void saveCourseInfo(CourseInfoVo courseInfoVo);
    //查询课程信息
    CourseInfoVo getCourseInfo(String courseId);
    //查询所有课程
    List<EduCourse> getAllCourseInfo();
    //修改课程基本信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);
    //根据课程id查询课程确认信息
    CoursePublishVo publishCourseInfo(String id);
    //删除课程
    void removeCourse(String courseId);
}
