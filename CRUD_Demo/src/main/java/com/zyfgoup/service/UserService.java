package com.zyfgoup.service;

import com.zyfgoup.entity.User;

/**
 * @Author Zyfgoup
 * @Date 2020/5/7 15:50
 * @Description
 */
public interface UserService {

    User getByNameandPwd(String name,String password);

}
