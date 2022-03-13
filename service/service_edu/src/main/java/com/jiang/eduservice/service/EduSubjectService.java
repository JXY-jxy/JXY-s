package com.jiang.eduservice.service;

import com.jiang.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Shawn
 * @since 2022-03-01
 */
public interface EduSubjectService extends IService<EduSubject> {
    //添加课程分类
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
