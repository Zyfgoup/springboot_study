package com.zyfgoup.comsumer;

import com.sun.xml.internal.bind.v2.TODO;
import com.zyfgoup.constant.RocketmqConstant;
import com.zyfgoup.entity.UserMsg;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @Author Zyfgoup
 * @Date 2020/12/14 16:14
 * @Description
 */
@Service
@RocketMQMessageListener(
        consumerGroup = "consumer2",
        topic = "test2"
)
public class MsgConsumer2 implements RocketMQListener<UserMsg> {
    @Override
    public void onMessage(UserMsg message) {
        System.out.println(System.currentTimeMillis());
        System.out.println(message.toString());
    }
}
