package com.atguigu.eduorder.service;

import com.atguigu.eduorder.pojo.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author qusheng
 * @since 2021-02-25
 */
public interface TOrderService extends IService<TOrder> {

    String createOrders(String courseId, String memberIdByJwtToken);
}
