package com.zyfgoup.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Zyfgoup
 * @Date 2020/12/11 16:39
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMsg {
    private long userId;
    private String name;
    private int age;
    private String address;

}
