package com.mymusic.music.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName File
 * @Description 传送文件的时候的文件实体类
 * @Author 86183
 * @Date2021-12-1115:06
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyFile implements Serializable {
    Integer id;
    String filename;
    String filepath;
    String size;//文件大小
    Date create_time;
}
