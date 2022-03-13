package com.jiang.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiang.commonutils.R;
import com.jiang.eduservice.entity.EduCourse;
import com.jiang.eduservice.entity.vo.CourseInfoVo;
import com.jiang.eduservice.entity.vo.CoursePublishVo;
import com.jiang.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Shawn
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //课程列表 条件查询分页
    @GetMapping("getCourseList/{current}/{limit}")
    public R getCourseList(@PathVariable long current,
                           @PathVariable long limit){
        Page<EduCourse> pageCourse=new Page<>(current,limit);
        courseService.page(pageCourse,null);
        long total=pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){

        courseService.saveCourseInfo(courseInfoVo);
        return R.ok();
    }
    //根据课程查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){

        CourseInfoVo courseInfoVo=courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }
    //修改课程基本信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }
    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo=courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }
    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }
    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

