package com.zyfgoup.controller;

import com.zyfgoup.entity.Person;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Zyfgoup
 * @Date 2020/5/29 14:17
 * @Description
 */

@RestController
public class TestSwagger2Controller {



    @ApiOperation("根据名字获取人")
    @GetMapping("/person/{name}")
    @ApiImplicitParam(name = "name",value = "人的名字",required = true,dataType = "String")
    public Map<String,Person> queryByName(@PathVariable("name") String name){

        Person person = new Person();
        person.setName("zyf");
        person.setAge(21);
        Map<String,Person> map = new HashMap();
        map.put(name,person);
       return map;
    }

    @PostMapping("/person")
    public String add(@RequestBody Person person){
        System.out.println(person);
        if(person.getAge()==21){
            return "success";
        }
        return "fail";
    }

    @PutMapping("/person/{id}")
    public String update(@PathVariable("id") int id, Person person){
        System.out.println("update:"+person);
        person.setName("Zyfgoup");
        if(id==1){
            return "success";
        }
        return "fail";
    }

    @DeleteMapping("/person/{id}")
    public String delete(@PathVariable("id") int id){
        System.out.println("delete:"+id);
        return "success";
    }

}
