package com.mymusic.music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mymusic.music.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
//    @Update("")
//    @Options(useGeneratedKeys = false)
    Integer closeOrder(@Param("orderNo") String orderNo);
}
