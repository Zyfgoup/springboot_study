package com.zyfgoup.config;

import com.alibaba.fastjson.JSONObject;
import com.zyfgoup.constant.RocketmqConstant;
import com.zyfgoup.entity.UserMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Zyfgoup
 * @Date 2020/12/21 17:34
 * @Description
 */

@Slf4j
@Configuration
public class RocketMqConfig {
    @Bean
    public DefaultMQPushConsumer userMQPushConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("bacthconsumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("testbatch", "*");
        // 设置每次消息拉取的时间间隔，单位毫秒
        consumer.setPullInterval(1000);
        // 设置每个队列每次拉取的最大消息数
        consumer.setPullBatchSize(24);
        // 设置消费者单次批量消费的消息数目上限
        consumer.setConsumeMessageBatchMaxSize(12);
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context)
                -> {
            List<UserMsg> userInfos = new ArrayList<>(msgs.size());
            log.info("批量消费信息 消费信息个数"+userInfos.size());
            Map<Integer, Integer> queueMsgMap = new HashMap<>(8);
            msgs.forEach(msg -> {
                userInfos.add(JSONObject.parseObject(msg.getBody(), UserMsg.class));
                queueMsgMap.compute(msg.getQueueId(), (key, val) -> val == null ? 1 : ++val);
            });
            log.info("userInfo size: {}, content: {}", userInfos.size(), userInfos);
        /*
          处理批量消息，如批量插入：userInfoMapper.insertBatch(userInfos);
         */
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        return consumer;
    }
}
