package com.zyfgoup.comsumer;
import com.alibaba.fastjson.JSON;
import com.zyfgoup.constant.RocketmqConstant;
import com.zyfgoup.entity.UserMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;


/**
 * @Author Zyfgoup
 * @Date 2020/12/11 16:43
 * @Description
 */

@Service
@RocketMQMessageListener(
        consumerGroup = RocketmqConstant.CONSUMER_GROUP,
        topic = "test"
)
@Slf4j
public class MsgConsumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt messageExt) {
       log.info("MsgConsumer >>> " + JSON.parse(messageExt.getBody())+"  MsgId >>>"+messageExt
       .getMsgId());
    }
}
