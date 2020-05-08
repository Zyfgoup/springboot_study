package com.zyfgoup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author Zyfgoup
 * @Date 2020/5/7 15:09
 * @Description
 */
@RestController
public class TestJdbcController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/test")
    public List<Map<String,Object>> test(){
        String sql = "select * from user";
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        return mapList;
    }
}
