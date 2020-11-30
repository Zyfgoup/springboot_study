package com.zyfgoup.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author Zyfgoup
 * @Date 2020/5/5 16:05
 * @Description
 */


@ApiModel("人")
@Component
@Data
@ConfigurationProperties(prefix = "person")
@Validated
public class Person {
  @Size(min = 3,max = 6)

  @ApiModelProperty("人的名字")
    private String name;
    private int age;
    private Boolean happy;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private Map<String,Dog> dog;


    public Person(){

    }

  public Person(@Size(min = 3, max = 6) String name, int age, Boolean happy, Date birth, Map dog) {
    this.name = name;
    this.age = age;
    this.happy = happy;
    this.birth = birth;
    this.dog  = dog;
  }
}
