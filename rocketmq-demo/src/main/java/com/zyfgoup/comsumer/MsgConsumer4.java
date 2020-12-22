package com.zyfgoup.comsumer;

import com.alibaba.fastjson.JSON;
import com.zyfgoup.constant.RocketmqConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @Author Zyfgoup
 * @Date 2020/12/15 14:42
 * @Description
 */
@Service
@RocketMQMessageListener(
        consumerGroup = "consumer4",
        topic = "test4"
)
@Slf4j
public class MsgConsumer4 implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt message) {
        log.info("MsgConsumer4 >>> " + JSON.parse(message.getBody())+"  MsgId >>>"+message
                .getMsgId());
    }
}
