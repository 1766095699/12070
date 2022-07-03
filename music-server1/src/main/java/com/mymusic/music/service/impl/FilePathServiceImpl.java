package com.mymusic.music.service.impl;


import com.mymusic.music.dao.FileMapper;
import com.mymusic.music.domain.MyFile;
import com.mymusic.music.service.FilePathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName FilePathServiceImpl
 * @Description TODO
 * @Author 86183
 * @Date2021-11-2815:53
 * @Version 1.0
 **/
@Service
public class FilePathServiceImpl implements FilePathService {
    @Autowired
    FileMapper fileMapper;

    /**
     * 插入文件
     * @param myFile
     * @return
     */
    @Override
    public Integer insertFilePath(MyFile myFile) {
        return fileMapper.insertFilePath(myFile);
    }

    /**
     * 通过文件名查询文件的详细信息
     *
     * @param filename
     * @return
     */
    @Override
    public MyFile getFile(String filename) {
        return fileMapper.getFile(filename);
    }
}
