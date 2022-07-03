package com.mymusic.myrabbitmq.dao;

import com.mymusic.myrabbitmq.po.TransMessagePO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

/**
 * @ClassName TransaMessageDao
 * @Description TODO
 * @Author 86183
 * @Date2022-03-1920:32
 * @Version 1.0
 **/
@Mapper
@Repository
public interface TransMessageDao {

    @Insert("replace INTO trans_message (id ,type, services, exchange, routing_key, queue, sequence, payload, date) " +
            "VALUES(#{id}, #{type}, #{services},#{exchange},#{routingKey},#{queue},#{sequence}, #{payload},#{date})")
    void insert(TransMessagePO transMessagePO);

    @Update("update trans_message set type =#{type}, services =#{services}, exchange =#{exchange}, " +
            "routing_key =#{routingKey}, queue =#{queue}, sequence =#{sequence}, payload =#{payload}, date =#{date} " +
            "where id=#{id} and services = #{services}")
    void update(TransMessagePO transMessagePO);

    @Select("SELECT id, type, services, exchange, routing_key routingKey, queue, sequence, payload, date FROM " +
            "trans_message " +
            "WHERE id = #{id} and services = #{services}")
    TransMessagePO selectByIdAndService(@Param("id") String id, @Param("services") String service);

    @Select("SELECT id, type, services, exchange, routing_key routingKey, queue, sequence, payload, date FROM " +
            "trans_message WHERE type = #{type} and services = #{services}")
    List<TransMessagePO> selectByTypeAndService(@Param("type") String type, @Param("services") String service);

    @Delete("DELETE FROM trans_message WHERE id = #{id} and services = #{services}")
    void delete(@Param("id") String id, @Param("services") String service);

}

