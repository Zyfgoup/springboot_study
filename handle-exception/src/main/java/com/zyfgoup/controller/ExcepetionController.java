package com.zyfgoup.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Zyfgoup
 * @Date 2020/11/30 17:29
 * @Description
 */
@RestController
public class ExcepetionController {

    @GetMapping("/test")
    public String test(){
        return "111111111";
    }
}
