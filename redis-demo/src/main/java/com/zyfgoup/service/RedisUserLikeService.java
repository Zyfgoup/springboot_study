package com.zyfgoup.service;

import com.zyfgoup.entity.UserLike;
import com.zyfgoup.entity.dto.LikedCountDTO;

import java.util.List;

/**
 * @Author Zyfgoup
 * @Date 2020/12/7 15:43
 * @Description
 * likedUserId 为被点赞的人的id
 * likePostId 为点赞人的id
 *
 *
 */
public interface RedisUserLikeService {

    /**
     * 点赞 状态为1
     *
     * @param likedUserId
     * @param likedPostId
     */
   void  saveLiked2Redis(String likedUserId, String likedPostId);

    /**
     * 取消点赞 状态为0
     *
     * @param likedUserId
     * @param likedPostId
     */
    void unlikeFormRedis(String likedUserId, String likedPostId);

    /**
     * 从Redis 删除一条点赞数据
     *
     * @param likedUserId
     * @param likedPostId
     */
    void deleteLikedFromRedis(String likedUserId, String likedPostId);

    /**
     * 该用户的被点赞数+1
     *
     * @param likedUserId
     */
    boolean incrLikedCount(String likedUserId) throws InterruptedException;


    /**
     * 该用户的被点赞数-1
     *
     * @param likedUserId
     */
    boolean decrLikedCount(String likedUserId) throws InterruptedException;

    /**
     * 从redis获取所有的点赞数据
     *
     * @return
     */
    List<UserLike> getLikedDataFromRedis();

    /**
     * 获取所有的点赞数量
     *
     * @return
     */
    List<LikedCountDTO> getLikedCountFromRedis();
}
