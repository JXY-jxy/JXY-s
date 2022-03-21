package com.jiang.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiang.commonutils.R;
import com.jiang.eduservice.entity.EduCourse;
import com.jiang.eduservice.entity.EduTeacher;
import com.jiang.eduservice.service.EduCourseService;
import com.jiang.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;
    //查询前8课，前4师
    @GetMapping("index")
    public R index(){

        List<EduCourse> eduList=courseService.getAllCourseInfo();
        List<EduTeacher> teacherList=teacherService.getAllTeacherInfo();

        return R.ok().data("eduList",eduList).data("teacherList",teacherList);
    }

}
