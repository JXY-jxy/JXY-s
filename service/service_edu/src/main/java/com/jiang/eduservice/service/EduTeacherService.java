package com.jiang.eduservice.service;

import com.jiang.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Shawn
 * @since 2022-02-24
 */
public interface EduTeacherService extends IService<EduTeacher> {
    //查询所有讲师
    List<EduTeacher> getAllTeacherInfo();
}
