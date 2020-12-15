package com.zyfgoup.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @Author Zyfgoup
 * @Date 2020/12/15 16:03
 * @Description
 */
@Mapper
@Component
public interface TransactionLogMapper {
    @Select("select count(1) from transaction_log where transaction_id = #{transactionId}")
     int get(@Param("transactionId") String transactionId);

    @Insert("insert into transaction_log(transaction_id) values(#{transactionId})")
    void insert(@Param("transactionId") String transactionId);
}
