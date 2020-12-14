package com.zyfgoup.comsumer;

import com.alibaba.fastjson.JSON;
import com.zyfgoup.constant.RocketmqConstant;
import com.zyfgoup.entity.UserMsg;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @Author Zyfgoup
 * @Date 2020/12/14 16:17
 * @Description
 */
@Service
@RocketMQMessageListener(
        consumerGroup = "consumer3",
        topic = "test3",
        consumeMode = ConsumeMode.ORDERLY
)
public class MsgConsumer3 implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt message) {
        System.out.println(JSON.parse(message.getBody()));
    }
}
