package com.zyfgoup.controller;

import com.zyfgoup.entity.User;
import com.zyfgoup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @Author Zyfgoup
 * @Date 2020/5/7 15:53
 * @Description
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(User user, Model model, HttpSession session){
        User admin = userService.getByNameandPwd(user.getUsername(),user.getPassword());

        if(admin==null){
            model.addAttribute("msg","用户名或者密码错误");
            return "index";
        }else{
            session.setAttribute("username",admin.getUsername());
            return "redirect:/main.html";
        }

    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index.html";
    }
}
