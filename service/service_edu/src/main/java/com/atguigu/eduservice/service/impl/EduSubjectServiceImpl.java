package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.pojo.EduSubject;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.pojo.excel.SubjectData;
import com.atguigu.eduservice.pojo.subject.OneSubject;
import com.atguigu.eduservice.pojo.subject.TwoSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author qusheng
 * @since 2021-01-26
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq(" parent_id","0");
        List<EduSubject> eduSubjects1 = baseMapper.selectList(wrapper);

        QueryWrapper<EduSubject> wrappe2 = new QueryWrapper<>();
        wrappe2.ne("parent_id","0");
        List<EduSubject> eduSubjects2 = baseMapper.selectList(wrappe2);

        List<OneSubject> lsits = new ArrayList<>();

        for (int i=0;i< eduSubjects1.size();i++){
            EduSubject subject = eduSubjects1.get(i);
            OneSubject oneSubject = new OneSubject();
            oneSubject.setId(subject.getId());
            oneSubject.setTitle(subject.getTitle());
            lsits.add(oneSubject);

            List<TwoSubject> finalTwoSubject = new ArrayList<>();
            for (int j = 0; j < eduSubjects2.size(); j++) {

                EduSubject subjectsss = eduSubjects2.get(j);

                TwoSubject twoSubject2 = new TwoSubject();
                if(subject.getId().equals(subjectsss.getParentId())){
                    twoSubject2.setId(subjectsss.getId());
                    twoSubject2.setTitle(subjectsss.getTitle());
                    finalTwoSubject.add(twoSubject2);
                }

            }
            oneSubject.setChildren(finalTwoSubject);

        }
        System.out.println("lsits:"+lsits.size());
        return lsits;
    }
}
