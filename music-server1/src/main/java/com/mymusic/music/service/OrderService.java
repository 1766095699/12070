package com.mymusic.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mymusic.music.domain.Order;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
public interface OrderService extends IService<Order> {

    //1 生成订单的方法
    String createOrders(String courseId, String memberIdByJwtToken);

    //超时关闭订单
    Integer closeOrder(String orderId);

    boolean isExistOrder(String userid,String songid);
}
