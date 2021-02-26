package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.pojo.EduSubject;
import com.atguigu.eduservice.pojo.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if(data==null){
             throw new MyException(20001,"读取的文件为空");
        }

        EduSubject eduSubject = this.existOneSubject(subjectService, data.getOneSubjectName());
        if(eduSubject==null){
            eduSubject = new EduSubject();
            eduSubject.setParentId("0");
            eduSubject.setTitle(data.getOneSubjectName());
            subjectService.save(eduSubject);

        }
        String pid = eduSubject.getId();
        EduSubject eduSubject2 = this.existTwoSubject(subjectService, data.getTwoSubjectName(),pid);
        if(eduSubject2==null){
            eduSubject2 = new EduSubject();
            eduSubject2.setParentId(pid);
            eduSubject2.setTitle(data.getTwoSubjectName());
            subjectService.save(eduSubject2);

        }
    }
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String bid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",bid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }



    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
