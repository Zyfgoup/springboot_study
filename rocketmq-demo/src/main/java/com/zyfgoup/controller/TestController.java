package com.zyfgoup.controller;

import com.alibaba.fastjson.JSON;
import com.zyfgoup.entity.UserMsg;
import com.zyfgoup.producer.MsgProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Zyfgoup
 * @Date 2020/12/14 10:54
 * @Description
 */
@RestController
public class TestController {

    @Autowired
    MsgProducer msgProducer;
    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @GetMapping("/testRocketmq")
    public void testRocketmq(){
        for (int i = 0; i < 1000; i++) {
            //msgProducer.sendUserMsg(UserMsg.builder().userId(10000+i).address("shenzhen").age(22).name("zouyongfa").build());

            //批量测试
            rocketMQTemplate.syncSend("testbatch",JSON.toJSONString(UserMsg.builder().userId(10000+i).address("shenzhen").age(22).name("zouyongfa").build()));
        }
    }

    @GetMapping("/testAsync")
    public void testAsync(){
        int i = 0;
            UserMsg userMsg = UserMsg.builder()
                    .userId(10000 + i)
                    .name("zyfgoup" + i)
                    .age(10 + i)
                    .address("shanghai")
                    .build();
            rocketMQTemplate.asyncSend("test", JSON.toJSON(userMsg), new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult.getSendStatus());
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("----------"+throwable.getMessage());
                }
            });
    }


    @GetMapping("/testOrder")
    public void testOrder(){
        int i = 0;
        for (; i < 10; i++) {
            UserMsg userMsg = UserMsg.builder()
                    .userId(10000 + i)
                    .name("zyfgoup" + i)
                    .age(10 + i)
                    .address("shanghai")
                    .build();

            //顺序消费的话 根据hashKey这个值的hash值进行分到哪个队列 并且得是同步的
            rocketMQTemplate.syncSendOrderly("test3", JSON.toJSON(userMsg),"userId");
        }
    }

    @GetMapping("/testDelay")
    public void testDelay(){
        int i = 0;
        for (; i < 10; i++) {
            UserMsg userMsg = UserMsg.builder()
                    .userId(10000 + i)
                    .name("zyfgoup" + i)
                    .age(10 + i)
                    .address("shanghai")
                    .build();
            //1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            //这里发的是Message 消费者里监听的类型要改成对应的实体类
            rocketMQTemplate.syncSend("test2", MessageBuilder.withPayload(userMsg).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build(),2000,5);
            System.out.println(System.currentTimeMillis());
        }
    }
}
