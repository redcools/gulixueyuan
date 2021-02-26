package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.pojo.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author qusheng
 * @since 2021-01-26
 */
@RestController
@RequestMapping("/eduservicer/edu-video")
@CrossOrigin
public class EduVideoController {

    @Resource
    private EduVideoService eduVideoService;
    @Resource
    private VodClient vodClient;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo  video){
        eduVideoService.save(video);
        return R.ok();
    }

    //TODO
    @DeleteMapping("deleteVideoId/{videoId}")
    public R deleteVideoId(@PathVariable String  videoId){
        //更加小节id得到视频id
        EduVideo eduVideo = eduVideoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        //是否有视屏
        if(!StringUtils.isEmpty(videoSourceId)){
            //视频远程调用
           R result = vodClient.removeAlyVideo(videoSourceId);
           if(result.getCode() == 20001){
               throw new MyException(20001,"删除视频失败，熔断器。。。。");
           }
        }


        //删小节
        eduVideoService.removeById(videoId);
        return R.ok();
    }

    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo  video){
        eduVideoService.updateById(video);
        return R.ok();
    }

    //修改前的数据回显
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String  videoId){
        EduVideo video =eduVideoService.selectId(videoId);
        return R.ok().data("video",video);
    }
}

