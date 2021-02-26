package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.eduservice.pojo.EduCourse;
import com.atguigu.eduservice.pojo.EduTeacher;
import com.atguigu.eduservice.pojo.frontVo.CourseFrontVo;
import com.atguigu.eduservice.pojo.frontVo.CourseWebVo;
import com.atguigu.eduservice.pojo.vo.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservicer/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Resource
    private EduCourseService courseService;
    @Resource
    private EduChapterService chapterService;

    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page,
                                @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> teacherPage = new Page<>(page,limit);
        Map<String ,Object> map = courseService.getCourseFrontList(teacherPage,courseFrontVo);
        return R.ok().data(map);
    }

    @GetMapping("getFrontCourseInfo/{courceId}")
    public R getFrontCourseInfo(@PathVariable String courceId){

        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courceId);

        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courceId);
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVoList",chapterVoList);
    }

    //根据课程id查询课晨信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo baseCourseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(baseCourseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }

}
