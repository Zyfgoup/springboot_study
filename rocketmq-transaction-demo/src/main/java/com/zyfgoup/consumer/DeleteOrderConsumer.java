package com.zyfgoup.consumer;

import com.alibaba.fastjson.JSON;
import com.zyfgoup.entity.Goods;
import com.zyfgoup.mapper.GoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @Author Zyfgoup
 * @Date 2020/12/15 16:34
 * @Description
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "deleteOrderConsumer",topic = "deleteOrder")
public class DeleteOrderConsumer implements RocketMQListener<String> {
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public void onMessage(String msg) {
        log.info("删除订单的消息正在被消费");
        String[] strings = msg.split("::");
        Goods build = Goods.builder().goodsCode(strings[1]).amount(Integer.valueOf(strings[2])).build();
        goodsMapper.update(build);
        log.info("消费成功");
    }
}
