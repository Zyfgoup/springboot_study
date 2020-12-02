package com.zyfgoup.controller;

import com.zyfgoup.entity.Person;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Zyfgoup
 * @Date 2020/12/2 9:30
 * @Description
 */

@RestController
@RequestMapping("/aop")
public class AopController {


    @GetMapping("/testaop/{str}/{age}")
    public Map<String, Person> test(@PathVariable("str") String str, @PathVariable("age") int age){
        System.out.println("我是controller层方法");
        Person p = new Person(str,age);
        Map<String, Person> res = new HashMap<>();
        res.put("zouyongfa",p);
        return res;
    }
}
