package com.atguigu.eduservice.service;

import com.atguigu.eduservice.pojo.EduChapter;
import com.atguigu.eduservice.pojo.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author qusheng
 * @since 2021-01-26
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    void addChapter(EduChapter eduChapter);

    Boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String id);
}
