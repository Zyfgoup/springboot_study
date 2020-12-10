package com.zyfgoup.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Zyfgoup
 * @Date 2020/12/7 15:53
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikedCountDTO {
    private String likedUserId;
    private Integer likedCount;
}
