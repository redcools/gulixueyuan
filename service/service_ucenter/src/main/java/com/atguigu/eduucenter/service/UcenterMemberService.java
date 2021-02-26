package com.atguigu.eduucenter.service;

import com.atguigu.eduucenter.pojo.UcenterMember;
import com.atguigu.eduucenter.pojo.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author qusheng
 * @since 2021-02-22
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);
}
