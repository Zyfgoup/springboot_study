package com.zyfgoup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.zyfgoup.utils.enums.LikedStatusEnum;
import io.swagger.models.auth.In;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户点赞表
 * </p>
 *
 * @author zyfgoup
 * @since 2020-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class UserLike implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 被点赞的用户id
     */
    private String likedUserId;

    /**
     * 点赞的用户id
     */
    private String likedPostId;

    /**
     * 点赞状态，0取消，1点赞
     */
    private Integer status = LikedStatusEnum.UNLIKE.getCode();

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(update = "now()")
    private LocalDateTime updateTime;

    public UserLike(String likedUserId, String likedPostId, Integer status){
        this.likedUserId = likedUserId;
        this.likedPostId = likedPostId;
        this.status = status;
    }


}
