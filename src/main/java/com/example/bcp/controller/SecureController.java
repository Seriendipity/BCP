package com.example.bcp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {
    //查询用户信息,需要有JWT才能访问
    @RequestMapping("/secure/getUserInfo")
    public String login(HttpServletRequest request){
        String userName = request.getAttribute("username").toString();
        String password = request.getAttribute("password").toString();
        return "当前用户信息 " + userName + " 密码" + password ;
    }
}
