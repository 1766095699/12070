package com.music.musicsongs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.music.musicsongs.dao.SingerMapper;
import com.music.musicsongs.pojo.Singer;
import com.music.musicsongs.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SingerService
 * @Description TODO
 * @Author 86183
 * @Date2022-06-0723:50
 * @Version 1.0
 **/
@Service
public class SingerServiceImpl implements SingerService {
    @Autowired
    private SingerMapper singerMapper;
    /**
     * 获取歌手详情
     *
     * @param id
     * @return
     */
    @Override
    public Singer getSingerDetail(Integer id) {
        Singer singer = singerMapper.selectById(id);
        return singer;
    }

    /**
     * 获取前五个歌手用于首页渲染
     */
    @Override
    public List<Singer> getSingerOf5() {
        QueryWrapper<Singer>queryWrapper = new QueryWrapper();
        queryWrapper.last("limit 0,5");
        List<Singer> singers = singerMapper.selectList(queryWrapper);
        return singers;
    }
}
