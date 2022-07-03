package com.mymusic.music.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName User
 * @Description TODO
 * @Author 86183
 * @Date2021-12-0810:34
 * @Version 1.0
 **/

@Data
public class User {
    private Integer id;
    private String username;
    private String nickname;
    private String avatar;
    private String phone_number;
}
