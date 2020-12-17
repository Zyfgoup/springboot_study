package com.zyfgoup.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.zyfgoup.entity.UserLike;
import com.zyfgoup.entity.dto.LikedCountDTO;
import com.zyfgoup.service.RedisUserLikeService;
import com.zyfgoup.utils.RedisKeyUtils;
import com.zyfgoup.utils.enums.LikedStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.data.MapEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author Zyfgoup
 * @Date 2020/12/7 15:55
 * @Description
 */

@Service
@Slf4j
public class RedisUserLikedImpl implements RedisUserLikeService {
    public static final Long SUCCESS_RELEASE = 1L;
    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 存的时候加锁 其实可以直接用一个命令 setnx
     * 修改的时候 才需要加锁去操作
     *
     * 仔细想想记录点赞状态 其实不需要原子性
     * 并发多个客户端 同样的点赞者点赞同样的人 重复设置最多也就是覆盖了 并不会有多条记录
     *
     * 只有计算点赞数+1 -1时需要考虑并发 多个客户端同样的账号同时点赞 或者取消时 计数时要加锁 有一个客户端计算了即可。
     *
     * @param likedUserId
     * @param likedPostId
     * @return
     */
    @Override
    public void saveLiked2Redis(String likedUserId, String likedPostId){

        String key = RedisKeyUtils.getLikedKey(likedUserId,likedPostId);
       redisTemplate.opsForHash().put
                (RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.LIKE.getCode());

//        String lockKey = key+"1";
//        String requestId = String.valueOf(UUID.randomUUID());
//        Boolean flag = redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, 5, TimeUnit.SECONDS);
//        //拿到锁
//        if(flag){
//            redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key,LikedStatusEnum.LIKE.getCode());
//
//            //lua脚本释放锁
//            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//            RedisScript of = RedisScript.of(script,Long.class);
//            Long res= (Long)redisTemplate.execute(of,Collections.singletonList(lockKey), requestId);
//            if(SUCCESS_RELEASE.equals(res))
//                return true;
//        }
//        return false;
    }

    /**
     * 取消点赞 同上
     * @param likedUserId
     * @param likedPostId
     */
    @Override
    public void unlikeFormRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId,likedPostId);
        redisTemplate.opsForHash().put
                (RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.UNLIKE.getCode());
    }

    @Override
    public void deleteLikedFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId,likedPostId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED,key);
    }


    /**
     * 要加锁 假设多个客户端同样的账号点赞  那么被点赞的用户的点赞量只是+1
     * 用当前的值作为requestId
     * @param likeUserId
     * @return
     */
    @Override
    public boolean incrLikedCount(String likeUserId){
        String requestId = UUID.randomUUID().toString();
        String lockKey = likeUserId+"+1";
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, 5, TimeUnit.SECONDS);
        if(flag) {
            redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likeUserId, 1);

            new Thread(()->{
                //lua脚本释放锁
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                RedisScript of = RedisScript.of(script,Long.class);
                try {
                    //3分钟
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Long res= (Long)redisTemplate.execute(of,Collections.singletonList(lockKey), requestId);
                log.info("锁释放了");
            }).start();
           return true;
        }
        return false;
    }

    @Override
    public boolean decrLikedCount(String likedUserId) throws InterruptedException {
        String requestId = UUID.randomUUID().toString();
        String lockKey = likedUserId+"-1";
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, 5, TimeUnit.SECONDS);
        if(flag) {
            redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likedUserId, -1);
            //lua脚本释放锁
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            RedisScript of = RedisScript.of(script,Long.class);
            Long res= (Long)redisTemplate.execute(of,Collections.singletonList(lockKey), requestId);
            if(SUCCESS_RELEASE.equals(res))
                return true;

        }
        return false;

    }

    @Override
    public List<UserLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<UserLike> list = new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            String key = (String)entry.getKey();
            String[] strings = key.split("::");
            String likedUserId = strings[0];
            String likedPostId = strings[1];
            Integer value =(Integer)entry.getValue();

            UserLike userLike = new UserLike(likedUserId,likedPostId,value);
            list.add(userLike);

            //从redis删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED,key);
        }
        return list;
    }

    @Override
    public List<LikedCountDTO> getLikedCountFromRedis() {

        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, ScanOptions.NONE);
        List<LikedCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
            String key = (String) map.getKey();
            LikedCountDTO dto = new LikedCountDTO(key, (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, key);
        }
        return list;
    }

}
