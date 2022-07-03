package com.mymusic.music.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName Result
 * @Description 用于登陆响应给浏览器的数据LJH
 * @Author 86183
 * @Date2021-08-0419:39
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
public class Result {
    private boolean flag;
    private String message;
}
