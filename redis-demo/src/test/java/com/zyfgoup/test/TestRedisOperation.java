package com.zyfgoup.test;

import com.zyfgoup.controller.UserLikeController;
import com.zyfgoup.service.RedisUserLikeService;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RunAs;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Zyfgoup
 * @Date 2020/12/9 17:04
 * @Description
 */
@RunWith(value = SpringRunner.class)
@SpringBootTest
public class TestRedisOperation {

    @Autowired
    RedisUserLikeService redisUserLikeService;

    //引入 ContiPerf 进行性能测试
    @Rule
    public ContiPerfRule contiPerfRule = new ContiPerfRule();
    static int count = 1;
    @Test
    @PerfTest(invocations = 1000,threads = 200)
    public void testunLiked() throws InterruptedException {
        redisUserLikeService.unlikeFormRedis("zouyongfa","wwx");
        redisUserLikeService.decrLikedCount("zouyongfa");
    }

    @Test
    @PerfTest(invocations = 1000,threads = 100)
    public void testLiked() throws InterruptedException {
        redisUserLikeService.saveLiked2Redis("zouyongfa","wwx");
        redisUserLikeService.incrLikedCount("zouyongfa");
    }
}
