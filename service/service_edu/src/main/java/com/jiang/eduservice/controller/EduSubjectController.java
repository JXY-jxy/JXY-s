package com.jiang.eduservice.controller;


import com.jiang.commonutils.R;
import com.jiang.eduservice.entity.subject.OneSubject;
import com.jiang.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Shawn
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来文件，读取文件内容
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //上传过来Excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    //课程分类列表（树形
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //list集合泛型是一级分类（一级包括多个二级
        List<OneSubject> list=subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

