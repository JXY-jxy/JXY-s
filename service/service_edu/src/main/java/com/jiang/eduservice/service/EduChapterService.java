package com.jiang.eduservice.service;

import com.jiang.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Shawn
 * @since 2022-03-01
 */
public interface EduChapterService extends IService<EduChapter> {


    List<ChapterVo> getChapterVideoByCourseId(String courseId);
    //删除章节的方法
    boolean deleteChapter(String chapterId);
    //2 根据课程id删除章节
    void removeChapterByCourseId(String courseId);
}
