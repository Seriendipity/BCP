package com.example.bcp.controller;

import com.example.bcp.entity.Homework;
import com.example.bcp.entity.StudentHomework;

import com.example.bcp.mapper.HomeworkMapper;
import com.example.bcp.mapper.StudentHomeworkMapper;
import com.example.bcp.mapper.StudentHomeworkMapper;
import com.example.bcp.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.bcp.entity.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/homework")
public class HomeworkController {
    @Autowired
    private HomeworkMapper homeworkMapper;
    @Autowired
    private StudentHomeworkMapper studentHomeworkMapper;

    //返回某cid对应所有homework
    @GetMapping("/allCidHomework")
    public Result selectByCid(@RequestParam String cid){
//        String username = request.getAttribute("username").toString();
        List<Homework> homeworks = homeworkMapper.selectByCid(cid);
        Map<String,Object> responseData = new HashMap<>();
        for(Homework homework:homeworks){
            responseData.put("HomeworkNO",homework.getHomeworkNo());
            responseData.put("HomeworkDescription",homework.getHomeworkDescription());
            responseData.put("HomeworkStratTime",homework.getStartTime());
            responseData.put("HomeworkEndTime",homework.getEndTime());
        }
        return Result.success(responseData);
    }

    @GetMapping("/allStudentHomework")
    public Result selectByHomeworkNO(@RequestParam String homeworkNo){
//        String username = request.getAttribute("username").toString();
        List<StudentHomework> studentHomeworks = studentHomeworkMapper.selectByHomeworkNo(homeworkNo);
        Map<String,Object> responseData = new HashMap<>();
        for(StudentHomework studentHomework:studentHomeworks){
            responseData.put("StudentNo",studentHomework.getStudentNo());
            responseData.put("StudentDescription()",studentHomework.getSubmitDescription());
            responseData.put("StudentHomeworkSubmitTime",studentHomework.getSubmitTime());
        }

        return Result.success(responseData);
    }



    //对某student的作业打分
    @PostMapping("/updateGrade")
    public Result updateGrade(@RequestBody Map<String, Object> requestData){
        int submitGrade = (int)requestData.get("SubmitGrade");
        String studentNo = (String)requestData.get("StudentNo");
        String homeworkNo = (String)requestData.get("HomeworkNo");
        studentHomeworkMapper.updateStudentHomeworkSubmitGrade(submitGrade,studentNo,homeworkNo);
        return Result.success();
    }


}
