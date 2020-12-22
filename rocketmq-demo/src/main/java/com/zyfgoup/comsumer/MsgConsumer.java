package com.zyfgoup.comsumer;
import com.alibaba.fastjson.JSON;
import com.zyfgoup.constant.RocketmqConstant;
import com.zyfgoup.entity.UserMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.apache.rocketmq.spring.support.RocketMQConsumerLifecycleListener;
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
public class MsgConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {


    @Override
    public void onMessage(MessageExt messageExt) {
       log.info("MsgConsumer >>> " + JSON.parse(messageExt.getBody())+"  MsgId >>>"+messageExt
       .getMsgId());
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {

        //削峰时可以使用  消息发送到broker 然后消费者慢慢消费即可
         //每次拉取的间隔，单位为毫秒
        consumer.setPullInterval(2000);
         //设置每次从队列中拉取的消息数为16  拉取的消息总数 = 16*写队列*broker数
        consumer.setPullBatchSize(16);
    }
}
