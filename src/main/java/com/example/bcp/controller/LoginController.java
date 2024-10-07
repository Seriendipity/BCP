package com.example.bcp.controller;

import com.example.bcp.service.StudentService;
import com.example.bcp.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;

@RestController
public class LoginController {
    @Autowired
    private StudentService studentService;

    @RequestMapping("/login")
    public String login(String userName , String password){
        if(studentService.selectByStudentNo(userName) == null ||
                !studentService.selectByStudentNo(userName).getPassword().equals(password)
        ){
            System.out.println("用户名或密码错误");
            return "";
        }
        String token = JwtUtils.createToken(userName,password);
        return token;
    }
}
