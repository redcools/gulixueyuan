package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.pojo.EduCourse;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.pojo.EduCourseDescription;
import com.atguigu.eduservice.pojo.EduTeacher;
import com.atguigu.eduservice.pojo.frontVo.CourseFrontVo;
import com.atguigu.eduservice.pojo.frontVo.CourseWebVo;
import com.atguigu.eduservice.pojo.vo.CourseInfoVo;
import com.atguigu.eduservice.pojo.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author qusheng
 * @since 2021-01-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Resource
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Resource
    private EduVideoService eduVideoService;

    @Resource
    private EduChapterService eduChapterService;

    @Override
    public String  saveCourseInfo(CourseInfoVo vo) {
        EduCourse eduCourse = new EduCourse();
          BeanUtils.copyProperties(vo,eduCourse);
        int i = baseMapper.insert(eduCourse);
        if(i<=0){
            throw  new MyException(20001,"添加课程信息失败");
        }
        String  cid = eduCourse.getId();

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(vo.getDescription());
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);

        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if(i==0){
            throw new MyException(20001,"修改课程信息失败");
        }


        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(eduCourseDescription,eduCourse);
        boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);

    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {

        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    public void removeCourse(String id) {
        eduVideoService.removeVidoByCourseId(id);

        eduChapterService.removeChapterByCourseId(id);
        eduCourseDescriptionService.removeById(id);

        int result = baseMapper.deleteById(id);
        if(result==0){
            throw new MyException(20001,"无视频，删除失败");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam, wrapper);
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courceId) {
        return baseMapper.getBaseCourseInfo(courceId);
    }
}
