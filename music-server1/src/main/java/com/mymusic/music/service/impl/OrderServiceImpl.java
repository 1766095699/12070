package com.mymusic.music.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mymusic.music.dao.OrderMapper;
import com.mymusic.music.domain.Order;
import com.mymusic.music.domain.Song;
import com.mymusic.music.domain.User;
import com.mymusic.music.service.OrderService;
import com.mymusic.music.service.SongService;
import com.mymusic.music.service.UserService;
import com.mymusic.music.utils.OrderNoUtil;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private SongService songService;

    @Autowired
    private UserService userService;
    @Autowired
    private OrderMapper orderMapper;
    //1 生成订单的方法
    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用根据用户id获取用户信息
        User userInfoOrder = userService.getUserById(Integer.valueOf(memberId));

        //通过远程调用根据课程id获取课信息

        Song songInfo = songService.selectByPrimaryKey(Integer.valueOf(courseId));
        //创建Order对象，向order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(songInfo.getName().split("-")[1]);
        order.setCourseCover(songInfo.getPic());
        order.setTeacherName(songInfo.getName().split("-")[0]);
        order.setTotalFee(songInfo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getPhone_number());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
//        baseMapper.select
        baseMapper.insert(order);
         //返回订单号
        return order.getOrderNo();
    }

    @Override
    public Integer closeOrder(String orderId) {
        System.out.println("order信息为："+orderId);
//        if(order1.getIsDeleted()==false&&order1.getStatus()==0){//未支付且未删除且超时的的订单进行更新
        Order order = new Order();
//        order.setIsDeleted(1);
//        UpdateWrapper<Order>updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("order_no",orderId).eq("status",0 ).eq("is_deleted",0 );
//        int update = baseMapper.update(order, updateWrapper);
//        return update;
        QueryWrapper<Order>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderId);

        System.out.println("order的详细信息:"+baseMapper.selectList(queryWrapper));
        return orderMapper.closeOrder(orderId);//这里我直接统一把过期未删除的订单全部逻辑删除了
//        }
//        return false;
    }

    @Override
    public boolean isExistOrder(String userid,String songid){
        QueryWrapper<Order>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",songid);
        queryWrapper.eq("member_id",userid);
        int cnt = baseMapper.selectCount(queryWrapper);
        if(cnt==0){
            return false;
        }
        return true;
    }


}
