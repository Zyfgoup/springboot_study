用户发起点赞、取消点赞后先存入 Redis 中，再每隔两小时从 Redis读取点赞数据写入数据库中做持久化存储。
点赞功能在很多系统中都有，但别看功能小，想要做好需要考虑的东西还挺多的。
点赞、取消点赞是高频次的操作，若每次都读写数据库，大量的操作会影响数据库性能，所以需要做缓存。
至于多久从 Redis 取一次数据存到数据库中，根据项目的实际情况定吧，我是暂时设了两个小时。
项目需求需要查看都谁点赞了，所以要存储每个点赞的点赞人、被点赞人，不能简单的做计数。

``` 
create table `user_like`(
    `id` int not null auto_increment,
    `liked_user_id` varchar(32) not null comment '被点赞的用户id',
    `liked_post_id` varchar(32) not null comment '点赞的用户id',
    `status` tinyint(1) default'1' comment '点赞状态，0取消，1点赞',
    `create_time` timestamp not nulldefault current_timestamp comment '创建时间',
  `update_time` timestamp not nulldefault current_timestamp on update current_timestamp comment '修改时间',
    primary key(`id`),
    INDEX `liked_user_id`(`liked_user_id`),
    INDEX `liked_post_id`(`liked_post_id`)
) comment '用户点赞表';
```
记录用户被点赞数量的表就没写了 demo中也没具体实现

---
启用Redis
@EnableCaching

设点赞人的 id 为 likedPostId，被点赞人的 id 为 likedUserId ，点赞时状态为 1，取消点赞状态为 0。将点赞人 id 和被点赞人 id 作为键，两个 id 中间用 :: 隔开，点赞状态作为值。
所以如果用户点赞，存储的键为：likedUserId::likedPostId，对应的值为 1 。取消点赞，存储的键为：likedUserId::likedPostId，对应的值为 0 。取数据时把键用 :: 切开就得到了两个id，也很方便。

点赞 取消赞不需要考虑多个客户端 同样的用户同时点击的并发问题 最多就是覆盖值 点赞状态都是1 或者0
如果是点赞数+1 -1 就需要考虑多个客户端的相同用户同时点击时的并发问题了 
解决方案：
1.采用延时双删的方法解决
测试 发现少量的并发时没问题  但是如果请求多点的时候 由于锁已经删除了后面的请求进来还是能拿到锁 那么还是会有重复+1
但是实际应用也不会有那么多个客户端 相同的账号同时点赞吧
2.不需要实时精准点赞人数时 可使用定时任务
如果不需要实时显示点赞的人数 其实可以不要计算 一段时间查一次数据库 然后将数据存在redis上也行
或者如果点赞数不需要那么精准的话 就不管重复的+1-1了 定时任务一段时间去查数据库的点赞表查某用户的点赞量然后来修正redis数据就好了
毕竟存点赞的人是不会有并发问题的 
3.不释放锁
如果点赞了 就设置一个锁 一个请求拿到锁了其他请求进来肯定拿不到锁 就不会重复+1 -1了
但是这样每个用户被点赞都要设置一个键值对来作为锁  很快redis就会多很多key是无用的
4.延时更久
或者使用mq来实现异步  发送延时消息去删除锁 拿到锁的客户端点赞成功了先返回响应  过一段消息消费了去释放锁
或者是拿到锁的客户端 new个线程睡一会去释放锁(实际中用线程池的线程去执行) 而原本的线程已返回响应了
逻辑上只要睡得够久 并发的请求都返回来了 不会重复+1 -1的

这是demo 具体看是要业务场景来实现吧

单纯的记录点赞状态的方法都是不需要加锁的 我这里是把记录点赞人等信息和点赞数+-分开作为不同方法实现的


可能会想到点赞只需要setnx记录点赞状态 然后拿到锁 这样锁有了 key也不是无用的 然后+1就不会有并发问题了
但是这样是第一次点赞才适用，如果取消点赞了(只是把状态设置为0) 然后再点赞时这个key hkey已经存在了
当然也可以取消点赞就把这个key hkey删除 便可以实现
但是取消点赞仍然要考虑-1 其实也只是省去了一半

怎么加锁 释放锁 可百度 redis分布式锁



---
redisTemplate在使用时使用的是JSON序列化的方式
如果传入的值不超过int范围时  拿到的值类型转换不能是Long 哪怕传入的是Long型


---
测试并发时 在测试类中用线程池不知道为什么不管用 redis内的数据不会变化 但是单次调用又正常
用contiperf测试也是正常的

---
使用mybatis plus 自动生成代码时  对应生成的mapper启动的时候无法扫描到 不知道是不是因为子模块的因素
要使用MapperScan注解

生成的实体类一定要有无参构造参数  @Data注解不包含 踩过几次坑了 一直记不住
@NoArgsConstructor

如果要更新时 触发数据库的on update current_timestamp 需要单独更新某些字段 不能整个更新
或者是使用TableField(update="now()")

---
监听程序关闭的钩子 然后去执行一次将Redis数据存到db时 空指针
使用DisposableBean 就可以 详见src\main\java\com\zyfgoup\entity\MyDisposableBean.java
可能到关闭钩子时  Bean这些都注销了 所以空指针

定时任务更新DB 就不写了  用Quartz即可 百度教程
