package com.music.musicsongs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName ListSong
 * @Description TODO
 * @Author 86183
 * @Date2022-06-080:18
 * @Version 1.0
 **/
@Data
@TableName("list_song")
public class ListSong {
    @TableId(type = IdType.AUTO)
    Integer id;
    @TableField("song_id")
    Integer songId;
    @TableField("song_list_id")
    Integer songListId;
    @TableField("user_id")
    Integer userId;
}
