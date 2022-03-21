package com.jiang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiang.eduservice.entity.EduCourse;
import com.jiang.eduservice.entity.EduCourseDescription;
import com.jiang.eduservice.entity.vo.CourseInfoVo;
import com.jiang.eduservice.entity.vo.CoursePublishVo;
import com.jiang.eduservice.mapper.EduCourseMapper;
import com.jiang.eduservice.service.EduChapterService;
import com.jiang.eduservice.service.EduCourseDescriptionService;
import com.jiang.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.eduservice.service.EduVideoService;
import com.jiang.servicebase.exceptionhandler.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Shawn
 * @since 2022-03-01
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    //课程描述注入
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    //注入小节和章节的service
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    //添加课程基本信息
    @Override
    public void saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1 向课程表加数据
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert==0){
            //添加失败
            throw new ServiceException(20001,"添加课程失败");
        }
        //获取添加之后课程id
        String cid=eduCourse.getId();

        //2 向课程简介表添加课程简介
        EduCourseDescription courseDescription=new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id（课程与描述1对1
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo=new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2 查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //查询所有课程基本信息
    @Cacheable(value = "banner", key = "'selectCourseList'")
    @Override
    public List<EduCourse> getAllCourseInfo() {
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> list=eduCourseService.list(wrapper);
        return  list;
    }


    //修改课程基本信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {

        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update==0){
            throw new ServiceException(20001,"修改课程信息失败");
        }

        //2 修改描述表
        EduCourseDescription courseDescription=new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }
    //删除课程
    @Override
    public void removeCourse(String courseId) {

        //1 根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        //2 根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);

        //3 根据课程id删除描述
        courseDescriptionService.removeById(courseId);

        //4 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result==0){
            throw new ServiceException(20001,"删除失败");
        }
    }


}
