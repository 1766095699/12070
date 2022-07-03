package com.mymusic.music.aoptest;

import java.util.Objects;

/**
 * @ClassName User
 * @Description TODO
 * @Author 86183
 * @Date2022-01-1019:36
 * @Version 1.0
 **/
public class User {
    String username;
    String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
