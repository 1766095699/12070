package com.mymusic.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.mymusic.music.domain.Song;
import com.mymusic.music.service.SongService;

import com.mymusic.music.utils.Consts;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import sun.invoke.util.VerifyAccess;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @ClassName SongController
 * @Description TODO
 * @Author 86183
 * @Date2021-07-2615:31
 * @Version 1.0
 **/
@RestController
@RequestMapping("/song")
public class SongController {
    @Autowired
    private SongService songService;

    /**
     * 添加歌曲
     */
     @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSong(HttpServletRequest request, @RequestParam("file")MultipartFile mpFile){
         JSONObject jsonObject = new JSONObject();
         String singerId = request.getParameter("singerId").trim();
         String name = request.getParameter("name").trim();          //歌名
         String introduction = request.getParameter("introduction").trim();          //简介
         String pic = "/img/songPic/hhh.jpg";                     //默认图片
         String lyric = request.getParameter("lyric").trim();     //歌词
         //上传歌曲文件
         while(mpFile.isEmpty()){//上传失败
             jsonObject.put(Consts.CODE,0);
             jsonObject.put(Consts.MSG,"歌曲上传失败" );
             return jsonObject;
         }

         //文件名
         String fileName = System.currentTimeMillis()+mpFile.getOriginalFilename();
         //文件路径
         String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
         //如果文件路径不存在，新增该路径
         File file1 = new File(filePath);
         if(!file1.exists()){
           file1.mkdir();
         }
         //实际文件地址
         //存储到数据库里的相对文件地址
         String storeUrlPath = "/song/"+fileName;
         File dest = new File(filePath+System.getProperty("file.separator")+fileName);
         try {
             mpFile.transferTo(dest);
             Song song = new Song();
             song.setSingerId(Integer.parseInt(singerId));
             song.setName(name);
             song.setIntroduction(introduction);
             song.setPic(pic);
             song.setLyric(lyric);
             song.setUrl(storeUrlPath);
             System.out.println("comin");
             System.out.println(song);
             boolean flag = songService.insert(song);
             System.out.println(flag);
             System.out.println(song);
             if(flag){
                 jsonObject.put(Consts.CODE,1);
                 jsonObject.put(Consts.MSG,"保存成功");
                 jsonObject.put("avatar",storeUrlPath);
                 return jsonObject;
             }
             jsonObject.put(Consts.CODE,0);
             jsonObject.put(Consts.MSG,"保存失败");
             return jsonObject;
         } catch (IOException e) {
             jsonObject.put(Consts.CODE,0);
             jsonObject.put(Consts.MSG,"保存失败"+e.getMessage());
         }finally {
             return jsonObject;
         }
     }

    /**
     * 修改歌曲信息
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSong(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();          //主键
        String name = request.getParameter("name").trim();      //歌名
        String introduction = request.getParameter("introduction").trim();//专辑
        String lyric = request.getParameter("lyric").trim();    //歌词

        //保存到歌手的对象中
        Song song = new Song();
        song.setId(Integer.parseInt(id));
        song.setName(name);
        song.setIntroduction(introduction);
        song.setLyric(lyric);
        boolean flag = songService.update(song);
        if(flag){   //保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;
    }
    /**
     * 根据歌手id查询歌曲
     */
    @RequestMapping(value = "/singer/detail",method = RequestMethod.GET)
    public Object songOfSingerId(HttpServletRequest request){
        String singerId = request.getParameter("singerId");
        return songService.songOfSingerId(Integer.parseInt(singerId));
    }
    /**
     * 根据id删除歌曲信息
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteSinger(HttpServletRequest request){
        //-TODO 先查询到数据库中对应的文件地址，删除掉它再进行下面的代码
        String id = request.getParameter("id").trim();          //主键
        boolean flag = songService.delete(Integer.parseInt(id));
        return flag;
    }

    /**
     * 更新歌曲
     */
    @RequestMapping(value = "updateSongUrl",method = RequestMethod.POST)
    public Object updateSongUrl(@RequestParam("file") MultipartFile avatorFile,@RequestParam("id")Integer id){
        JSONObject jsonObject = new JSONObject();
        //看看文件是否存在
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"修改失败");
        }
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"song";
        //如果文件路径不存在，新增该路径
        File file1  = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/song/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Song song = new Song();
            song.setUrl(storeAvatorPath);
            song.setId(id);
            boolean flag = songService.update(song);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("avatar",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0 );
            jsonObject.put(Consts.MSG,"修改失败" );
            return jsonObject;
        }
        finally {
            return jsonObject;
        }
    }

    /**
     * 更新歌曲图片
     */
    @RequestMapping(value = "/updateSongPic",method = RequestMethod.POST)
    public Object updateSongPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        //文件名=当前时间到毫秒+原来的文件名
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        //文件路径
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"songPic";
        //如果文件路径不存在，新增该路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/songPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setPic(storeAvatorPath);
            boolean flag = songService.update(song);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }

    /**
     * 根据歌曲id查询歌曲对象
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Object detail(HttpServletRequest request){
        String songId = request.getParameter("songId");
        return songService.selectByPrimaryKey(Integer.parseInt(songId));

    }

    /**
     * 根据歌手名字查询歌曲
     */
    @RequestMapping(value = "/songOfSongName",method = RequestMethod.GET)
    public Object songOfSongName(HttpServletRequest request){
        String songName = request.getParameter("songName");
        return songService.songOfName(songName);
    }

    /**
     * 获取所有歌曲
     */
    @GetMapping("/getAllSong")
    public Object getAllSong(HttpServletRequest request){
        return songService.allSong();
    }

    /**
     * 根据歌手名字模糊查询歌曲
     */
    @RequestMapping(value = "/likeSongOfName",method = RequestMethod.GET)
    public Object likeSongOfName(HttpServletRequest request){
        String songName = request.getParameter("songName");
        return songService.likeSongOfName(songName);
    }

    @GetMapping("getHotRankSongList")
    public Object getHotRankSongList(){
        HashMap<String,Object>map = new HashMap<>();
        map.put("data", songService.getHotRankSongList());
        return map;
    }
}
