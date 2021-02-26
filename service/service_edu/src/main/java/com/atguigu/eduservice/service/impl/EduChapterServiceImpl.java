package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.pojo.EduChapter;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.pojo.EduVideo;
import com.atguigu.eduservice.pojo.vo.ChapterVo;
import com.atguigu.eduservice.pojo.vo.VideoVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.querydsl.QuerydslRepositoryInvokerAdapter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author qusheng
 * @since 2021-01-26
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper1);

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();;
        wrapper.eq("course_id",courseId);
        List<EduVideo> eduVideos = eduVideoService.list(wrapper);

        List<ChapterVo> finalList = new ArrayList<>();


        for (int i = 0; i < eduChapters.size(); i++) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapters.get(i),chapterVo);
            finalList.add(chapterVo);

                List<VideoVo> videoVoList = new ArrayList<>();
            for (int j = 0; j < eduVideos.size(); j++) {
                if(eduVideos.get(j).getChapterId().equals(eduChapters.get(i).getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideos.get(j),videoVo);
                    videoVoList.add(videoVo);
                }

            }
            chapterVo.setChildren(videoVoList);
        }

        return finalList;
    }

    @Override
    public void addChapter(EduChapter eduChapter) {

    }

    @Override
    public Boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(videoQueryWrapper);
        if(count>0){
            throw new MyException(20001,"不能删除");
        }else{
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }
    }

    @Override
    public void removeChapterByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
