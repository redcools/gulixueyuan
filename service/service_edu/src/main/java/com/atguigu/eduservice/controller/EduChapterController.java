package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.pojo.EduChapter;
import com.atguigu.eduservice.pojo.vo.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.context.annotation.Role;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author qusheng
 * @since 2021-01-26
 */
@RestController
@RequestMapping("/eduservicer/edu-chapter")
@CrossOrigin
public class EduChapterController {

    @Resource
    private EduChapterService eduChapterService;

    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String  courseId){
        List<ChapterVo> list= eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    @GetMapping("getChapterInfo/{chapterId}")
    public  R getChapterInfo(@PathVariable String  chapterId){
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @DeleteMapping("deleteByid/{chapterId}")
    public R deleteByid(@PathVariable String  chapterId){
        Boolean aBoolean = eduChapterService.deleteChapter(chapterId);
        if(aBoolean){
            return R.ok();
        }else{
            return R.error();
        }

    }
}

