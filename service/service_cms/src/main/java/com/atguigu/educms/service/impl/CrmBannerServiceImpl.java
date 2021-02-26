package com.atguigu.educms.service.impl;

import com.atguigu.educms.pojo.CrmBanner;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author qusheng
 * @since 2021-02-21
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    @Cacheable(key = "'selectIndexList'",value ="banner")
    //查询所有banner
    @Override
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");
        return baseMapper.selectList(wrapper);
    }
}
