package com.zyfgoup.producer;

import com.zyfgoup.mapper.TransactionLogMapper;
import com.zyfgoup.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * @Author Zyfgoup
 * @Date 2020/12/15 16:26
 * @Description
 */
@Slf4j
@RocketMQTransactionListener
public class DeleteOrderListener implements RocketMQLocalTransactionListener {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    TransactionLogMapper transactionLogMapper;

    /**
     * 执行本地事务
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("执行本地事务");
        String orderNo = (String) msg.getHeaders().get("orderNo");
        String transactionId = (String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        try {
            //删除订单 写本地事务表 应该作为一个事务执行
            orderService.deleteOrder(orderNo,transactionId);
            log.info("本地事务执行完毕 commit");
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e){
            log.info("本地事务执行失败 rollback");
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }

    /**
     * 回查本地事务是否成功提交
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        //检查本地事务表 是否有这个transactionId
        int i = transactionLogMapper.get((String) msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID));
        if(i==1){
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
