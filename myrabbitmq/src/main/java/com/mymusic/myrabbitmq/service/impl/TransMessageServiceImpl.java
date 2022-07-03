package com.mymusic.myrabbitmq.service.impl;

import com.mymusic.myrabbitmq.dao.TransMessageDao;
import com.mymusic.myrabbitmq.enumType.TransMessageType;
import com.mymusic.myrabbitmq.po.TransMessagePO;
import com.mymusic.myrabbitmq.service.TransMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TransMessageServiceImpl implements TransMessageService {

    @Autowired
    TransMessageDao transMessageDao;

    @Value("${db.services}")
    String serviceName;

    /**
     * 发送前暂存消息
     *
     * @param exchange exchange
     * @param routingKey routingKey
     * @param body body
     * @return TransMessagePO
     */
    @Override
    public TransMessagePO messageSendReady(String exchange, String routingKey, String body) {
        final String messageId = UUID.randomUUID().toString();
        TransMessagePO transMessagePO = new TransMessagePO();
        transMessagePO.setId(messageId);
        transMessagePO.setService(serviceName);
        transMessagePO.setExchange(exchange);
        transMessagePO.setRoutingKey(routingKey);
        transMessagePO.setPayload(body);
        transMessagePO.setDate(new Date());
        transMessagePO.setSequence(0);
        transMessagePO.setType(TransMessageType.SEND);
        transMessageDao.insert(transMessagePO);
        return transMessagePO;
    }

    /**
     * 设置消息发送成功
     *
     * @param id 消息ID
     */
    @Override
    public void messageSendSuccess(String id) {
        transMessageDao.delete(id, serviceName);//我做了逻辑删除,将type改成了success
    }

    /**
     * 设置消息返回
     * @param id id
     * @param exchange  exchange
     * @param routingKey routingKey
     * @param body body
     * @return TransMessagePO
     */
    @Override
    public TransMessagePO messageSendReturn(String id, String exchange, String routingKey, String body) {
        TransMessagePO transMessagePO = new TransMessagePO();
        transMessagePO.setId(UUID.randomUUID().toString());
        transMessagePO.setService(serviceName);
        transMessagePO.setExchange(exchange);
        transMessagePO.setRoutingKey(routingKey);
        transMessagePO.setPayload(body);
        transMessagePO.setDate(new Date());
        transMessagePO.setSequence(0);
        transMessagePO.setType(TransMessageType.SEND);
        transMessageDao.insert(transMessagePO);
        return transMessagePO;
    }


    /**
     * 记录消息发送次数
     * @param id
     */
    @Override
    public void messageResend(String id) {
        TransMessagePO transMessagePO = transMessageDao.selectByIdAndService(id, serviceName);
        transMessagePO.setSequence(transMessagePO.getSequence() + 1);
        transMessageDao.update(transMessagePO);
    }

    @Override
    public List<TransMessagePO> listReadyMessages() {
        return transMessageDao.selectByTypeAndService(TransMessageType.SEND.name(), serviceName);
    }

    /**
     * 保存监听到的死信消息
     * @param id
     * @param exchange
     * @param routingKey
     * @param queue
     * @param body
     */
    @Override
    public void messageDead(String id, String exchange, String routingKey, String queue, String body) {
        TransMessagePO transMessagePO = new TransMessagePO();
        transMessagePO.setId(id);
        transMessagePO.setService(serviceName);
        transMessagePO.setExchange(exchange);
        transMessagePO.setRoutingKey(routingKey);
        transMessagePO.setPayload(body);
        transMessagePO.setDate(new Date());
        transMessagePO.setSequence(0);
        transMessagePO.setType(TransMessageType.DEAD);
        transMessageDao.insert(transMessagePO);
    }

    /**
     * 消息重发多次，放弃
     * @param id id
     */
    @Override
    public void messageDead(String id) {
        TransMessagePO transMessagePO = transMessageDao.selectByIdAndService(id, serviceName);
        transMessagePO.setType(TransMessageType.DEAD);
        transMessageDao.update(transMessagePO);
    }

    /**
     * 消息保存前消费
     */
    @Override
    public TransMessagePO messageReceiveReady(String id, String exchange, String routingKey, String queue,
                                              String body) {
        TransMessagePO transMessagePO = transMessageDao.selectByIdAndService(id, serviceName);
        if (null == transMessagePO) {
            transMessagePO = new TransMessagePO();
            transMessagePO.setId(id);
            transMessagePO.setService(serviceName);
            transMessagePO.setExchange(exchange);
            transMessagePO.setRoutingKey(routingKey);
            transMessagePO.setQueue(queue);
            transMessagePO.setPayload(body);
            transMessagePO.setDate(new Date());
            transMessagePO.setSequence(0);
            transMessagePO.setType(TransMessageType.RECEIVE);
            transMessageDao.insert(transMessagePO);
        } else {
            transMessagePO.setSequence(transMessagePO.getSequence() + 1);
            transMessagePO.setType(TransMessageType.RECEIVE);
            transMessageDao.update(transMessagePO);
        }
        return transMessagePO;
    }

    @Override
    public void messageReceiveSuccess(String id) {
        transMessageDao.delete(id, serviceName);
    }
}
