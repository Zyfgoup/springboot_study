package com.zyfgoup.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Zyfgoup
 * @Date 2020/12/15 15:37
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Goods {
    private Long id;
    private String goodsCode;
    private String goodsName;
    private int amount;
}
