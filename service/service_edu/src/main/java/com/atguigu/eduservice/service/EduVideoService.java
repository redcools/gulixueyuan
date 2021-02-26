package com.atguigu.eduservice.service;

import com.atguigu.eduservice.pojo.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author qusheng
 * @since 2021-01-26
 */
public interface EduVideoService extends IService<EduVideo> {

    EduVideo selectId(String videoId);

    void removeVidoByCourseId(String id);
}
