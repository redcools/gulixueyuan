package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.pojo.EduCourse;
import com.atguigu.eduservice.pojo.EduTeacher;
import com.atguigu.eduservice.pojo.vo.CourseInfoVo;
import com.atguigu.eduservice.pojo.vo.CoursePublishVo;
import com.atguigu.eduservice.pojo.vo.PublishCourseVo;
import com.atguigu.eduservice.pojo.vo.VoTeacherQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author qusheng
 * @since 2021-01-26
 */
@RestController
@RequestMapping("/eduservicer/educourse")
@CrossOrigin
public class EduCourseController {

    @Resource
    private EduCourseService eduCourseService;

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody  CourseInfoVo vo){
       String id =  eduCourseService.saveCourseInfo(vo);
        return R.ok().data("courseid",id);
    }

    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
       CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
}

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo) ;
        return R.ok();
    }

    //根据课程id查询课时消息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo =  eduCourseService.publishCourseInfo(id);

        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    //最终发布;修改课程状态
    @PostMapping("publishCourse/{id}")
        public R publishCourse(@PathVariable String  id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//修改为发布
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //课成立表
    @GetMapping("getCourseList")
    public R getCourseList(){
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list",list);
    }

    //课程列表分页条件查询
    @ApiOperation(value = "课程列表分页条件查询")
    @PostMapping("pageCourse/{pageNo}/{pageSize}")
    public R pageCourse(@ApiParam(name = "pageNo", value = "当前页", required = true)
                                  @PathVariable Integer pageNo,
                                  @ApiParam(name = "pageSize", value = "每页条数", required = true)
                                  @PathVariable Integer pageSize,
                                  @RequestBody(required = false) PublishCourseVo vo) {
        Page<EduCourse> page = new Page<>(pageNo, pageSize);
        //条件
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(vo.getTitle())) {
            queryWrapper.like("title", vo.getTitle());
        }
        if (!StringUtils.isEmpty(vo.getStatus())) {
            queryWrapper.eq("status", vo.getStatus());
        }

        queryWrapper.orderByDesc("gmt_create");


        eduCourseService.page(page, queryWrapper);
        long total = page.getTotal();//总数据条数
        List<EduCourse> records = page.getRecords();//数据list集合
        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);
    }

    @DeleteMapping("deleteCourse/{id}")
    public R deleteCourse(@PathVariable String  id){
        eduCourseService.removeCourse(id);
        return R.ok();
    }
}

