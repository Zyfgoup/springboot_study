package com.zyfgoup;

import com.zyfgoup.service.UserLikeService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @Author Zyfgoup
 * @Date 2020/12/7 15:15
 * @Description
 */
@SpringBootApplication
@EnableCaching
@MapperScan(value = "com.zyfgoup.mapper")
public class RedisDemoApplication{
    @Autowired
    static UserLikeService userLikeService;

    public static void main(String[] args) {


        SpringApplication springApplication = new SpringApplication(RedisDemoApplication.class);

//        //程序关闭的钩子函数
//        //关闭的时候更新redis到数据库
//        springApplication.addListeners(new ApplicationListener<ContextClosedEvent>() {
//            @Override
//            public void onApplicationEvent(ContextClosedEvent applicationEvent) {
//                userLikeService.transLikedFromRedis2Db();
//            }
//        });

        springApplication.run(args);
    }
}
