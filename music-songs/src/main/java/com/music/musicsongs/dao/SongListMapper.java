package com.music.musicsongs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.musicsongs.pojo.SongList;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌单Dao
 */
@Repository
public interface SongListMapper extends BaseMapper<SongList>{
    /**
     * 获取前8个歌单用于首页渲染
     * @return
     */
    List<SongList>getSongListOf8();



}
















