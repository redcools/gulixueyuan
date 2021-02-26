package com.atguigu.eduservice.service;

import com.atguigu.eduservice.pojo.EduCourse;
import com.atguigu.eduservice.pojo.frontVo.CourseFrontVo;
import com.atguigu.eduservice.pojo.frontVo.CourseWebVo;
import com.atguigu.eduservice.pojo.vo.CourseInfoVo;
import com.atguigu.eduservice.pojo.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author qusheng
 * @since 2021-01-26
 */
public interface EduCourseService extends IService<EduCourse> {

    String  saveCourseInfo(CourseInfoVo vo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String id);

    Map<String, Object> getCourseFrontList(Page<EduCourse> teacherPage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courceId);
}
