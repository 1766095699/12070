package com.music.musicsongs.controller;

import com.music.musiccommon.utils.R;
import com.music.musicsongs.bo.SongToSongListBo;
import com.music.musicsongs.bo.UserListSongsBo;
import com.music.musicsongs.pojo.Song;
import com.music.musicsongs.pojo.SongList;
import com.music.musicsongs.service.SongListService;
import com.music.musicsongs.service.SongService;
import com.music.musicsongs.vo.SongVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @ClassName SongListController
 * @Description TODO
 * @Author 86183
 * @Date2022-06-081:16
 * @Version 1.0
 **/
@RestController
@RequestMapping("/songlist")
@Slf4j
public class SongListController {
    @Autowired
    private SongListService songListService;
    @Autowired
    public SongService songService;


    /**
     * 查询官方歌单
     * @param id
     * @return
     */
    @GetMapping("/officialsonglist/{id}")
    public Object getOfficialSongsById(@PathVariable Integer id){
        List<Song> list = songService.getOfficialSongsById(id);
        log.info("songlist={}",list );
        List<SongVo> data = list.stream().map(item -> {
            SongVo songVo = new SongVo();
            BeanUtils.copyProperties(item, songVo);
            return songVo;
        }).collect(Collectors.toList());
        return R.ok(data);
    }

    /**
     * 查询用户某个歌单中的所有歌
     * @param userListSongsBo
     * @return
     */
    @Cacheable(value = "songlist",key = "'test'")
    @PostMapping("/usersonglist")
    public Object getSongsBySongListIdAndUserId(@RequestBody UserListSongsBo userListSongsBo){
        //TODO 理论上是要从token中获取到用户id然后然后做查询的,这里我先写死了。但是我感觉应该在用户认证和授权那里就要做拦截了,而不是在这里做拦截
        System.out.println(userListSongsBo);
        System.out.println("进入songlist");
        List<Song> list = songService.getSongsBySongListIdAndUserId(userListSongsBo.getSongListId());
        List<SongVo>datalist =  list.stream().map(item->{
            SongVo songVo = new SongVo();
            BeanUtils.copyProperties(item,songVo);
            return songVo;
        }).collect(Collectors.toList());
        return R.ok(datalist);
    }
    @GetMapping("/songlistOf8")
    public Object getSongListOf8(){
        List<SongList> songListOf8 = songListService.getSongListOf8();
        return R.ok(songListOf8);
    }

    @PostMapping("/SongToSongList")
    public Object insertSongToSongList(@RequestBody SongToSongListBo songToSongListBo){
        System.out.println(songToSongListBo);
        Boolean res = songListService.insertSongToSongList(songToSongListBo.getSongId(),songToSongListBo.getSongListId() ,songToSongListBo.getUserId() );
        /**
         * 返回插入歌曲是否成功
         */
        long start = System.currentTimeMillis();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<400000;i++){
                    try{
                        int songId =  (int)(Math.random()*3000000+1);
                        int songlistid =  (int)(Math.random()*200000+1);
                        int userid =  (int)(Math.random()*100000+1);//5W日活用户
                        songListService.insertSongToSongList(songId,songlistid,userid);
                        if(i%100==0) System.out.println(i+" "+Thread.currentThread().getId());
                    }catch (Exception e){
                        System.out.println(e);
                        continue;
                    }
                }
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++){
            executorService.submit(runnable);
        }
        if(Objects.isNull(res)||res.equals(true)){
            return R.error(500,"插入歌曲失败");
        }
        System.out.println(System.currentTimeMillis()-start);
        return R.ok();
    }

    @PostMapping("/deleteSongToSongList")
    public Object deleteSongToSongList(@RequestBody SongToSongListBo songToSongListBo){
        Boolean res = songListService.deleteSongToSongList(songToSongListBo.getSongId(),songToSongListBo.getSongListId() ,songToSongListBo.getUserId() );
        /**
         * 返回插入歌曲是否成功
         */
        if(Objects.isNull(res)||res.equals(true)){
            return R.error(500,"删除歌曲失败");
        }
        return R.ok();
    }

    /**
     * 测试：用于插入歌单数据
     * @param songList
     * @return
     */
    @PostMapping("/addSongList")
    public Object insertSongList(@RequestBody SongList songList){
        ExecutorService executors = Executors.newFixedThreadPool(10);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<500000;i++){
                    StringBuilder stringBuilder = new StringBuilder();
                    int cnt1 = (int)(Math.random()*30+1);
                    for(int j=1;j<=cnt1;j++){
                        stringBuilder.append(getRandomChar());
                    }
                    songList.setTitle(stringBuilder.toString());
                    cnt1 = (int)(Math.random()*50+1);
                    stringBuilder = new StringBuilder();
                    for(int j=1;j<=cnt1;j++){
                        stringBuilder.append(getRandomChar());
                    }
                    songList.setIntroduction(stringBuilder.toString());
                    if(i%4==0)
                        songList.setStyle("欧美");
                    else if(i%4==1)
                        songList.setStyle("BGM");
                    else if(i%4==2)
                        songList.setStyle("日韩");
                    else songList.setStyle("华语");
                    songList.setPic("/img/songListPic/109951163879964479.jpg");
//            songList.setIntroduction("史诗级震撼人心超燃BGM,每首都是本人精心挑选和最喜欢的，歌单歌曲是按我个人喜欢排序，持续更新中……");
                    int userid =  (int)(Math.random()*1000000+1);//10W日活用户
                    songList.setUserId(userid);
                    songListService.insertSongList(songList);
                    if(i%100==0) System.out.println(i+" "+Thread.currentThread().getId());
                }
            }
        };
        for(int k=0;k<10;k++){
            executors.submit(runnable);
        }

        return R.ok();
    }

    //随机生成汉字
    private static char getRandomChar() {
        String str = "";
        int hightPos; //
        int lowPos;

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("错误");
        }

        return str.charAt(0);
    }

}
