package com.mymusic.music.service;

public interface SignService {
    /**
     * 获取连续签到天数
     * @return
     */
    public Integer signData(String userid);

    /**
     * 签到接口
     * @return
     */
    public boolean sign(String userid);

}
