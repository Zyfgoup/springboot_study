package com.zyfgoup.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyfgoup.entity.UserLike;
import com.zyfgoup.mapper.UserLikeMapper;
import com.zyfgoup.service.RedisUserLikeService;
import com.zyfgoup.service.UserLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户点赞表 服务实现类
 * </p>
 *
 * @author zyfgoup
 * @since 2020-12-07
 */
@Service
public class UserLikeServiceImpl extends ServiceImpl<UserLikeMapper, UserLike> implements UserLikeService {


    @Autowired
    RedisUserLikeService redisUserLikeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void transLikedFromRedis2Db(){
        List<UserLike> likedDataFromRedis = redisUserLikeService.getLikedDataFromRedis();
        for (UserLike userLike : likedDataFromRedis) {
            Map<String,Object> map= new HashMap<>();
            map.put("liked_user_id",userLike.getLikedUserId());
            map.put("liked_post_id",userLike.getLikedPostId());
            UserLike one = this.getOne(new QueryWrapper<UserLike>().allEq(map));
            if(ObjectUtil.isNotNull(one)){
                one.setStatus(userLike.getStatus());
                this.updateById(one);
            }else{
                this.save(userLike);
            }
        }
    }




}
