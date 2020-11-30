package com.zyfgoup.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Zyfgoup
 * @Date 2020/5/5 13:57
 * @Description
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello SpringBoot";
    }
}
