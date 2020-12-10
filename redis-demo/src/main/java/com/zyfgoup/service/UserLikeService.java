package com.zyfgoup.service;

import com.zyfgoup.entity.UserLike;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户点赞表 服务类
 * </p>
 *
 * @author zyfgoup
 * @since 2020-12-07
 */
public interface UserLikeService extends IService<UserLike> {


    public void transLikedFromRedis2Db();
}
