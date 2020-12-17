package com.zyfgoup.controller;


import cn.hutool.core.thread.NamedThreadFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyfgoup.entity.UserLike;
import com.zyfgoup.mapper.UserLikeMapper;
import com.zyfgoup.service.RedisUserLikeService;
import com.zyfgoup.service.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 用户点赞表 前端控制器
 * </p>
 *
 * @author zyfgoup
 * @since 2020-12-07
 */
@RestController
@RequestMapping("/user-like")
public class UserLikeController {


    @Autowired
    RedisUserLikeService redisUserLikeService;

    @Autowired
    UserLikeService userLikeService;

    /**
     * 测试并发时的数据正确性
     * @throws InterruptedException
     */
    @GetMapping("/test")
    public boolean  test() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor
                (20, 40, 10L,
                        TimeUnit.SECONDS, new ArrayBlockingQueue<>(30)
                        , new NameThreadFactory());


        redisUserLikeService.saveLiked2Redis("zouyongfa","xxx");
        return redisUserLikeService.incrLikedCount("zouyongfa");

    }

    @GetMapping("/test1")
    public void testTransRedis2Db(){
        userLikeService.transLikedFromRedis2Db();
//        IPage<UserLike> iPage = new Page<>(1,10);
//        IPage<UserLike> page = userLikeService.page(iPage);
    }



    static class NameThreadFactory implements ThreadFactory{
        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t =  new Thread(r,"thread-"+mThreadNum.getAndIncrement());
            System.out.println(t.getName()+" has been created");
            return t;
        }
    }

}
