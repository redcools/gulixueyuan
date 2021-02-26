package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.pojo.EduCourse;
import com.atguigu.eduservice.pojo.frontVo.CourseWebVo;
import com.atguigu.eduservice.pojo.vo.CourseInfoVo;
import com.atguigu.eduservice.pojo.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author qusheng
 * @since 2021-01-26
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courceId);
}
