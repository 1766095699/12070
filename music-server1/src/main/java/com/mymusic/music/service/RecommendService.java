package com.mymusic.music.service;

import com.mymusic.music.bo.SongListByDay;

import java.util.List;

public interface RecommendService {
    public List<SongListByDay>getSongListByDay();
}
