package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.mapper.TOrderMapper;
import com.atguigu.eduorder.pojo.TOrder;
import com.atguigu.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author qusheng
 * @since 2021-02-25
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class TOrderController {

    @Resource
    private TOrderService orderService;

    //生成订单
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId,
                       HttpServletRequest request){
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        //创建点的，返回订单号
        String orderNo =  orderService.createOrders(courseId,memberIdByJwtToken);

        return R.ok().data("orderNo",orderNo);
    }

    //根据订单id获取订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String  orderId){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder tOrder = orderService.getOne(wrapper);
        return R.ok().data("tOrder",tOrder);
    }


}

