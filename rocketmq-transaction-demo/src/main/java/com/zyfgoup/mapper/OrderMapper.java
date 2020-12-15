package com.zyfgoup.mapper;

import com.zyfgoup.entity.Order;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @Author Zyfgoup
 * @Date 2020/12/15 15:39
 * @Description
 */
@Mapper
@Component
public interface OrderMapper {

    @Delete("delete from t_order where order_no = #{orderNo}")
    public void delete(@Param("orderNo")String orderNo);

    @Select("select * from t_order where order_no = #{orderNo}")
    public Order select4orderNo(@Param("orderNo")String orderNo);
}
