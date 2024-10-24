package com.example.bcp.controller;

import com.example.bcp.entity.Assistant;
import com.example.bcp.entity.Result;
import com.example.bcp.entity.Student;
import com.example.bcp.entity.Teacher;
import com.example.bcp.service.AssistantService;
import com.example.bcp.service.StudentService;
import com.example.bcp.service.TeacherService;
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

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AssistantService assistantService;
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> loginData) {
        String userName = loginData.get("username");
        String password = loginData.get("password");

        System.out.println(userName);
        System.out.println(password);
        // 验证学生身份
        Student student = studentService.selectByStudentNo(userName);
        if (student != null) {
            if (student.getPassword().equals(password)) {
                String token = JwtUtils.createToken(userName, password);
                System.out.println(token);
                return Result.success(token);
            } else {
                return Result.error("用户名或密码错误");
            }
        }

        // 验证教师身份
        Teacher teacher = teacherService.selectByTeacherNo(userName);
        if (teacher != null) {
            if (teacher.getPassword().equals(password)) {
                String token = JwtUtils.createToken(userName, password);
                System.out.println(token);
                return Result.success(token);
            } else {
                return Result.error("用户名或密码错误");
            }
        }

        Assistant assistant = assistantService.selectByAssistantNo(userName);
        if(assistant != null){
            if(assistant.getPassword().equals(password)){
                String token = JwtUtils.createToken(userName,password);
                System.out.println(token);
                return Result.success(token);
            }else {
                return Result.error("用户名或密码错误");
            }
        }
//       if (assistantService.selectByAssistantNo(userName) == null ||
//                ! assistantService.selectByAssistantNo(userName).getPassword().equals(password)) {
//            System.out.println(userName);
//            System.out.println(password);
//            return Result.error("用户名或密码错误") ;
//        }
//        String token = JwtUtils.createToken(userName, password);
//        System.out.println(token);
        return Result.success("用户名或密码错误");
    }

}
