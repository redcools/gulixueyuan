package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.pojo.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author qusheng
 * @since 2021-01-26
 */
@RestController
@RequestMapping("/eduservicer/edusubject")
@CrossOrigin
public class EduSubjectController {

    @Resource
    private EduSubjectService eduSubjectService;


    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();

        return R.ok().data("list",list);
    }
}

