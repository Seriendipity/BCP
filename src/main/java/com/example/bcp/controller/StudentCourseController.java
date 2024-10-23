package com.example.bcp.controller;

import com.example.bcp.entity.Result;
import com.example.bcp.entity.StudentCourse;
import com.example.bcp.entity.Teaching;
import com.example.bcp.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/StudentCourse")
public class StudentCourseController {
    @Autowired
    StudentCourseService studentCourseService;

    @Autowired
    TeachingService teachingService;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @Autowired
    ClassService classService;

    @GetMapping("/allStudent")
    private Result allStudent(@RequestParam String Cid,HttpServletRequest request) {
        List<StudentCourse> studentList = studentCourseService.selectByCid(Cid);
        String username = request.getAttribute("username").toString();
        Map<String, Object> responseData = new HashMap<>();
        int i = 1;
        String studentNo,studentName,classNo,className;
        int grade;

        for(StudentCourse sc : studentList){
            grade = sc.getGrade();
            studentNo = sc.getStudentNo();
            studentName = studentService.selectByStudentNo(studentNo).getStudentName();
            classNo = studentService.selectByStudentNo(studentNo).getClassNo();
            className = classService.selectByClassNo(classNo).getClassName();
            // 将学生信息放入一个子Map中
            Map<String, Object> studentInfo = new HashMap<>();
            studentInfo.put("index",i);
            studentInfo.put("studentNo", studentNo);
            studentInfo.put("studentName", studentName);
            studentInfo.put("grade", grade);
            studentInfo.put("classNo",classNo);
            studentInfo.put("className", className);
            responseData.put("student"+i,studentInfo);
            i++;
        }
        responseData.put("username",username);
        return Result.success(responseData);
    }

    @GetMapping(value = "/allCourse")
    private Result allCourse(@RequestParam String StudentNo, HttpServletRequest request) {
        List<StudentCourse> courseList = studentCourseService.selectByStudentNo(StudentNo);
        Map<String, Object> responseData = new HashMap<>();
        int i = 1;
        String cid,courseNo,courseName,picture;
        String username = request.getAttribute("username").toString();
        for(StudentCourse sc : courseList){
            cid = sc.getCid();
            courseNo = teachingService.selectByCid(cid).getCourseNo();
            courseName = courseService.selectByCourseNo(courseNo).getCourseName();
            picture = teachingService.selectByCid(cid).getPicture();
            // 将课程信息放入一个子Map中
            Map<String, Object> courseInfo = new HashMap<>();
            courseInfo.put("cid", cid);
            courseInfo.put("courseNo", courseNo);
            courseInfo.put("courseName", courseName);
            courseInfo.put("picture", picture);
            responseData.put("course"+i,courseInfo);
            i++;
        }
        responseData.put("username",username);
        return Result.success(responseData);
    }

}
