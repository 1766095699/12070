package com.mymusic.music.service;

import com.mymusic.music.domain.MyFile;

/**
 * @ClassName FilePathService
 * @Description TODO
 * @Author 86183
 * @Date2021-11-2815:52
 * @Version 1.0
 **/
public interface FilePathService {
    /**
     * 插入文件
     * @param myFile
     * @return
     */
    Integer insertFilePath(MyFile myFile);
    /**
     * 通过文件名查询文件的详细信息
     * @param filename
     * @return
     */
    MyFile getFile(String filename);
}
