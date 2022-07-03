package com.mymusic.music.dao;

import com.mymusic.music.domain.MyFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName FileMapper
 * @Description TODO
 * @Author 86183
 * @Date2021-11-2815:45
 * @Version 1.0
 **/
@Repository
public interface FileMapper {
    /**
     * 插入文件
     * @param file
     * @return
     */
    Integer insertFilePath(@Param("file") MyFile file);

    /**
     * 通过文件名查询文件的详细信息
     * @param filename
     * @return
     */
    MyFile getFile(@Param("filepath")String filename);
}
