package com.zyfgoup.producer;


import com.alibaba.fastjson.JSON;
import com.zyfgoup.entity.UserMsg;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;


/**
 * @Author Zyfgoup
 * @Date 2020/12/11 16:41
 * @Description
 */

@Component
public class MsgProducer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendUserMsg(UserMsg userMsg){
        SendResult test = rocketMQTemplate.syncSend("test", JSON.toJSON(userMsg),2000);
        //System.out.println(test.getMsgId()+"::"+test.getSendStatus());
    }



}
