package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.pojo.EduTeacher;
import com.atguigu.eduservice.pojo.vo.VoTeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.w3c.dom.stylesheets.LinkStyle;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author qusheng
 * @since 2021-01-21
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Resource
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "讲师列表")
    @GetMapping("findAll")
    public R findAll(){
        //异常测试
       /* try {
            int i = 10/0;
        }catch (Exception e){
            throw new MyException(555,"自定义异常。。。。");
        }*/

        List<EduTeacher> list = eduTeacherService.list(null);

        return R.ok().data("items",list);
    }
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("remoTeacherById/{id}")
    public R remoTeacherById(
            @ApiParam(name = "id",value = "讲师ID",required = true)
            @PathVariable String id){
        if(eduTeacherService.removeById(id)){
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation(value = "讲师列表分页")
    @GetMapping("pageTeacher/{pageNo}/{pageSize}")
    public R pageTeacher(
            @ApiParam(name = "pageNo",value = "当前页",required = true)
            @PathVariable Integer pageNo,
            @ApiParam(name = "pageSize",value = "每页条数",required = true)
                         @PathVariable Integer pageSize){
        Page<EduTeacher> page = new Page<>(pageNo,pageSize);
        eduTeacherService.page(page,null);
        long total = page.getTotal();//总数据条数
        List<EduTeacher> records = page.getRecords();//数据list集合

       /* Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);*/

        return R.ok().data("total",total).data("rows",records);
    }


    @ApiOperation(value = "讲师列表分页条件查询")
    @PostMapping("pageTeacherCondition/{pageNo}/{pageSize}")
    public R pageTeacherCondition(@ApiParam(name = "pageNo",value = "当前页",required = true)
                                      @PathVariable Integer pageNo,
                                  @ApiParam(name = "pageSize",value = "每页条数",required = true)
                                      @PathVariable Integer pageSize,
                                  @RequestBody(required = false) VoTeacherQuery vo){
        Page<EduTeacher> page = new Page<>(pageNo,pageSize);
        //条件
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(vo.getName())){
            queryWrapper.like("name",vo.getName());
        }
        if(!StringUtils.isEmpty(vo.getLevel())){
            queryWrapper.eq("level",vo.getLevel());
        }
        if(!StringUtils.isEmpty(vo.getBegin())){
            queryWrapper.ge("gmt_create",vo.getBegin());//大于
        }
        if(!StringUtils.isEmpty(vo.getEnd())){
            queryWrapper.le("gmt_modified",vo.getEnd());//小于
        }


        eduTeacherService.page(page,queryWrapper);
        long total = page.getTotal();//总数据条数
        List<EduTeacher> records = page.getRecords();//数据list集合
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody(required = true) EduTeacher eduTeacher ){
        if(eduTeacherService.save(eduTeacher)){
            return R.ok();
        }
        return R.error();
    }
    @ApiOperation(value = "根据讲师Id查询")
    @GetMapping(value = "getTeacherById/{id}")
    public R getTeacherById(@PathVariable Integer id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }


    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){

        boolean flay = eduTeacherService.updateById(eduTeacher);
        if(flay)return R.ok();

        return R.error();
    }
}

