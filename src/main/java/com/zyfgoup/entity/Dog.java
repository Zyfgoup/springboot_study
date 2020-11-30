package com.zyfgoup.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Zyfgoup
 * @Date 2020/5/5 16:04
 * @Description
 */
@Component
@Data
@ConfigurationProperties(prefix = "dog")
public class Dog {
    private String name;
    private int age;
}
