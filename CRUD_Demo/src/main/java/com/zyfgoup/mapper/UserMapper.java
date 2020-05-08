package com.zyfgoup.mapper;

import com.zyfgoup.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Zyfgoup
 * @Date 2020/5/7 15:44
 * @Description
 */
@Mapper
@Repository
public interface UserMapper {

    User getByNameandPwd(@Param("username")String username,@Param("password") String password);
}
