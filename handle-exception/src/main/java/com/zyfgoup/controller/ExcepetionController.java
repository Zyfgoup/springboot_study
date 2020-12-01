package com.zyfgoup.controller;

import com.zyfgoup.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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


    @GetMapping("testException")
    public void test1(){
        throw new ResourceNotFoundException(null);
    }
}
