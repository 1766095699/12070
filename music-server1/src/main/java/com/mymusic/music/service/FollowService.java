package com.mymusic.music.service;

import java.util.Set;

public interface FollowService {
    public Set<Integer> commonFollowList(Integer userid1, Integer userid2);
}
