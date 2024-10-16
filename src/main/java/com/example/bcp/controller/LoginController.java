package com.example.bcp.controller;

import com.example.bcp.entity.Result;
import com.example.bcp.service.StudentService;
import com.example.bcp.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private StudentService studentService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> loginData) {
        String userName = loginData.get("username");
        String password = loginData.get("password");

        System.out.println(userName);
        System.out.println(password);
        if (studentService.selectByStudentNo(userName) == null ||
                !studentService.selectByStudentNo(userName).getPassword().equals(password)) {
            System.out.println(userName);
            System.out.println(password);
            return Result.error("用户名或密码错误") ;
        }

        String token = JwtUtils.createToken(userName, password);
        System.out.println(token);
        return Result.success(token);
    }

}
