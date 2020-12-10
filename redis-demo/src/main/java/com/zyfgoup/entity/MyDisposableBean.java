package com.zyfgoup.entity;

import com.zyfgoup.service.UserLikeService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author Zyfgoup
 * @Date 2020/12/10 19:35
 * @Description
 */
@Component
public class MyDisposableBean  implements DisposableBean {
    @Autowired
    UserLikeService userLikeService;

    @Override
    public void destroy() throws Exception {
        userLikeService.transLikedFromRedis2Db();
    }
}
