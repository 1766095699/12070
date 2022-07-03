package com.mymusic.music.service.impl;

import com.mymusic.music.common.R;
import com.mymusic.music.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * @ClassName SignServiceImpl
 * @Description TODO
 * @Author 86183
 * @Date2022-03-1711:04
 * @Version 1.0
 **/
@Service
public class SignServiceImpl implements SignService {
    @Autowired
    RedisTemplate redisTemplate;
    /**
     * 获取连续签到天数
     *
     * @param userid
     * @return
     */
    @Override
    public Integer signData(String userid) {
        //        DateTimeFormatter.da
        Calendar calendar = Calendar.getInstance();
//        System.out.println(calendar.get(Calendar.YEAR));
//        System.out.println(calendar.get(Calendar.MONTH));//注意月是从0开始计算的
//        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
//        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
//        System.out.println(calendar.get(Calendar.HOUR));
        Integer month = calendar.get(Calendar.MONTH)+1;
        Integer year = calendar.get(Calendar.YEAR);
        Integer day = calendar.get(Calendar.DAY_OF_MONTH);
        BitFieldSubCommands bitFieldSubCommands = BitFieldSubCommands.create()
                .get(BitFieldSubCommands.BitFieldType.unsigned(day))
                .valueAt(0);
        String key = year+"_"+month+"_"+day+"_"+userid;
        List<Long> list = redisTemplate.opsForValue().bitField(key, bitFieldSubCommands);
        int signCount = 0;
//        System.out.println(list.get(0));
        long v = list.get(0) == null ? 0 : list.get(0);
        // 取低位连续不为0的个数即为连续签到次数，需考虑当天尚未签到的情况
        for (int i = day; i > 0; i--) {// i 表示位移次数
            // 右移再左移，如果等于自己说明最低位是 0，表示未签到
            if (v >> 1 << 1 == v) {
                // 低位为 0 且非当天说明连续签到中断了
                if (i != day) break;
            } else {
                // 签到了 签到数加1
                signCount += 1;
            }
            // 右移一位并重新赋值，相当于把最右边一位去除
            v >>= 1;
        }
        if (list == null || list.isEmpty()) {
            //TODO 抛出异常;
        }
        return signCount;
    }

    /**
     * 签到接口
     *
     * @param userid
     * @return
     */
    @Override
    public boolean sign(String userid) {
        return false;
    }
}
