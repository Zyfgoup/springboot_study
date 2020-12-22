RocketMQ的消息发送方式主要含
syncSend()同步发送  主要运用在比较重要一点消息传递/通知等业务
asyncSend()异步发送 通常用于对发送消息响应时间要求更高/更快的场景
sendOneWay()三种方式适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集。
只发送消息，不等待服务器响应，只发送请求不等待应答。此方式发送消息的过程耗时非常短，一般在微秒级别。
sendOneWay()也是异步发送，区别在于不需等待Broker返回确认，所以可能会存在信息丢失的状况，但吞吐量更高，
具体需根据业务情况选用。
性能：sendOneWay > asyncSend > syncSend
---

RocketMQ的Consumer获取消息是通过向Broker发送拉取请求获取的，
而不是由Broker发送Consumer接收的方式。
消费者实现RocketMQPushConsumerLifecycleListener接口 实现prepareStart方法即可设置一些拉取消息的参数

pullInterval：每次从Broker拉取消息的间隔，单位为毫秒
pullBatchSize：每次从Broker队列拉取到的消息数，该参数很容易让人误解，一开始我以为是每次拉取的消息总数，但测试过几次后确认了实质上是从每个队列的拉取数(源码上的注释文档真的很差，跟没有一样)，即Consume每次拉取的消息总数如下：
EachPullTotal=所有Broker上的写队列数和(writeQueueNums=readQueueNums) * pullBatchSize
单次pull消息的最大数目受broker存储的MessageStoreConfig.maxTransferCountOnMessageInMemory(默认为32)值限制，即若想要消费者从队列拉取的消息数大于32有效(pullBatchSize>32)
则需更改Broker的启动参数maxTransferCountOnMessageInMemory值。

consumeMessageBatchMaxSize：每次消费(即将多条消息合并为List消费)的最大消息数目，默认值为1，rocketmq-spring-boot-starter 目前不支持批量消费(2.1.0版本)

---
如果要实现批量消费则需要自定义DefaultMQPushConsumer并配置其consumeMessageBatchMaxSize属性。
consumerMessageBatchMaxSize的值 受限于 pullBatchSize
```
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
```