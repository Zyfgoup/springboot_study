package com.zyfgoup.controller;

import com.zyfgoup.entity.Order;
import com.zyfgoup.mapper.GoodsMapper;
import com.zyfgoup.mapper.OrderMapper;
import com.zyfgoup.mapper.TransactionLogMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author Zyfgoup
 * @Date 2020/12/15 15:58
 * @Description
 */

@RestController
@Slf4j
public class TestController {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    TransactionLogMapper transactionLogMapper;

    @Autowired
    RocketMQTemplate rocketMQTemplate;


    @GetMapping("/test")
    public void testMybatis(){

        orderMapper.delete("10000");
        log.info(String.valueOf(transactionLogMapper.get("11111")));
        transactionLogMapper.insert("12222");
    }

    @GetMapping("/order/delete/{orderNo}")
    public void delete(@PathVariable("orderNo") String orderNo){
        log.info("用户删除订单");
        Order order = orderMapper.select4orderNo(orderNo);
        Message<String> message = MessageBuilder.withPayload(order.getOrderNo()+"::"+order.getGoodsCode() + "::" + order.getAmount())
                .setHeader("orderNo",order.getOrderNo())
                .setHeader(RocketMQHeaders.TRANSACTION_ID, UUID.randomUUID().toString())
                .build();
        rocketMQTemplate.sendMessageInTransaction("deleteOrder",message,null);
    }
}
