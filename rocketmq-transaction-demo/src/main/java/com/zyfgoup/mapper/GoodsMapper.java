package com.zyfgoup.mapper;

import com.zyfgoup.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @Author Zyfgoup
 * @Date 2020/12/15 15:44
 * @Description
 */
@Mapper
@Component
public interface GoodsMapper {


    @Update("update goods set amount= amount+ #{amount} where goods_code = #{goodsCode}")
    void update(Goods goods);
}
