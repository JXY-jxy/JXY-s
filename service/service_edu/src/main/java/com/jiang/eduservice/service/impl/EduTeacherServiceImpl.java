package com.jiang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiang.eduservice.entity.EduCourse;
import com.jiang.eduservice.entity.EduTeacher;
import com.jiang.eduservice.mapper.EduTeacherMapper;
import com.jiang.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Shawn
 * @since 2022-02-24
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduTeacherService eduTeacherService;

    //查询所有讲师基本信息
    @Cacheable(value = "banner", key = "'selectTeacherList'")
    @Override
    public List<EduTeacher> getAllTeacherInfo() {
        QueryWrapper<EduTeacher> wrapperTeacher=new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> list=eduTeacherService.list(wrapperTeacher);
        return list;
    }

}
