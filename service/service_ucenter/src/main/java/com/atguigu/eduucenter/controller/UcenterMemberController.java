package com.atguigu.eduucenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.eduucenter.mapper.UcenterMemberMapper;
import com.atguigu.eduucenter.pojo.UcenterMember;
import com.atguigu.eduucenter.pojo.vo.RegisterVo;
import com.atguigu.eduucenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author qusheng
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/eduucenter/ucentermember")
@CrossOrigin
public class UcenterMemberController {
    @Resource
    private UcenterMemberService ucenterMemberService;

    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember ucenterMember){
       String token = ucenterMemberService.login(ucenterMember);
        return R.ok().data("token",token);
    }

    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return R.ok();

    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    //根据用户id获取用户信息
    @PostMapping("getUserInfo/{id}")
    public UcenterMemberOrder getUserInfo(@PathVariable String  id){
        UcenterMember member = ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);

        return ucenterMemberOrder;
    }

}

