package com.example.bcp.controller;

import com.example.bcp.entity.Result;
import com.example.bcp.entity.StudentCourse;
import com.example.bcp.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("StudentCourse")
public class StudentCourseController {
    @Autowired
    StudentCourseService studentCourseService;
    @GetMapping("allStudent/{Cid}")
    private Result allStudent(@PathVariable("Cid")String Cid) {
        List<StudentCourse> studentList = studentCourseService.selectByCid(Cid);
        return Result.success(studentList);
    }
}
