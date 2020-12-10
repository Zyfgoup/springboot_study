package com.zyfgoup.utils;

/**
 * @Author Zyfgoup
 * @Date 2020/12/7 15:56
 * @Description
 */
public class RedisKeyUtils {
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";
    public static final String MAP_KEY_USER_LIKED_COUNT = "MAP_USER_LIKED_COUNT";


    public static String getLikedKey(String likedUserId,String likedPostId){
        StringBuilder builder = new StringBuilder();
        builder.append(likedUserId);
        builder.append("::");
        builder.append(likedPostId);
        return builder.toString();
    }
}
