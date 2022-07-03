package com.music.musicsongs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName Album
 * @Description TODO
 * @Author 86183
 * @Date2022-06-0722:46
 * @Version 1.0
 **/
@Data
public class Album {
    //专辑id
    @TableId(type = IdType.AUTO)
    Integer id;
    //专辑名
    String name;
    //歌手
    Integer singerId;
    //发行日期
    Date publishDate;
    //专辑发行时间
    String publishCompany;
    //歌曲id
    Integer songId;
}
