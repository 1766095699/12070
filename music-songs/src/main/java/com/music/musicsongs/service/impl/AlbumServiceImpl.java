package com.music.musicsongs.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.music.musicsongs.dao.AlbumMapper;
import com.music.musicsongs.pojo.Album;
import com.music.musicsongs.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AlbumServiceImpl
 * @Description TODO
 * @Author 86183
 * @Date2022-06-0723:43
 * @Version 1.0
 **/
@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumMapper albumMapper;
    /**
     * 返回前5张专辑的数据
     *
     * @return
     */
    @Override
    public List<Album> getAlbumListOf5() {
        Wrapper<Album> last = new QueryWrapper<Album>().last("limit 0,5");
        List<Album> albums = albumMapper.selectList(last);
        return albums;
    }
}
