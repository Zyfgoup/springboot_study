package com.zyfgoup.service.impl;

import com.zyfgoup.mapper.OrderMapper;
import com.zyfgoup.mapper.TransactionLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Zyfgoup
 * @Date 2020/12/15 16:10
 * @Description
 */
@Service
@Slf4j
public class OrderServiceImpl {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    TransactionLogMapper transactionLogMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteOrder(String orderNo,String transactionId){
        log.info("本地删除订单");
        orderMapper.delete(orderNo);
        log.info("插入事务表");
        transactionLogMapper.insert(transactionId);
    }
}
