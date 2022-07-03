package com.mymusic.music.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mymusic.music.dao.SongMapper;
import com.mymusic.music.domain.Song;
import com.mymusic.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 歌曲service实现类
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {
    @Autowired
    private SongMapper songMapper;
    /**
     * 增加
     * @param song
     */
    @Override
    public boolean insert(Song song) {
        return songMapper.insert(song)>0;
    }

    /**
     * 修改
     *
     * @param song
     */
    @Override
    public boolean update(Song song) {
        return songMapper.update(song)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return songMapper.delete(id)>0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public Song selectByPrimaryKey(Integer id) {
        return songMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有歌曲
     */
    @Override
    public List<Song> allSong() {
        return songMapper.allSong();
    }

    /**
     * 根据歌名精确查询列表
     *
     * @param name
     */
    @Override
    public List<Song> songOfName(String name) {
        return songMapper.songOfName(name);
    }

    /**
     * 根据歌手id查询
     *
     * @param singerId
     */
    @Override
    public List<Song> songOfSingerId(Integer singerId) {
        return songMapper.songOfSingerId(singerId);
    }

    /**
     * 根据歌名模糊查询列表
     *
     * @param name
     */
    @Override
    public List<Song> likeSongOfName(String name) {
        return songMapper.likeSongOfName("%"+name+"%");
    }

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 获取前十的歌曲播放次数排行(理论上要按照类型板块来划分的,这里我就做个效果演示从全表查)
     */
    @Override
    public List<Song> getHotRankSongList() {
        QueryWrapper<Song>queryWrapper = new QueryWrapper<>();
        queryWrapper.select("singer_name").select("id").select("pic");
//        List<Song> hotRankSongList = (List<Song>) redisTemplate.opsForHash().get("Hot_Rank", "music1");

        List<Song> hotRankSongList = redisTemplate.opsForList().range("Hot_Rank",0 , -1);
        if(hotRankSongList!=null){
            System.out.println("redis中音乐排行榜数据有数据");
            return hotRankSongList;
        }
        List<Song> hotRankSongList1 =  songMapper.getHotRankSongList();
        List list = redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                for(int i=0;i<hotRankSongList1.size();i++){
                    ListOperations<String, Object> kvListOperations = (ListOperations<String, Object>) operations.opsForList();
                    kvListOperations.leftPush("Hot_Rank",hotRankSongList1.get(i));
                    redisTemplate.expire("Hot_Rank",60*60, TimeUnit.SECONDS);
                }
                return null;
            }
        });
        System.out.println("list="+list);
        return hotRankSongList1;
    }
}
