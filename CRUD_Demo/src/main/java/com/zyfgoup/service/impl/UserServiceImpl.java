package com.zyfgoup.service.impl;

import com.zyfgoup.entity.User;
import com.zyfgoup.mapper.UserMapper;
import com.zyfgoup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Zyfgoup
 * @Date 2020/5/7 15:52
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getByNameandPwd(String name, String password) {
        return userMapper.getByNameandPwd(name,password);
    }
}
