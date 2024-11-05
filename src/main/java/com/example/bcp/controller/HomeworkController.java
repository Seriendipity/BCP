package com.example.bcp.controller;

import com.example.bcp.entity.Homework;
import com.example.bcp.entity.StudentHomework;

import com.example.bcp.mapper.HomeworkMapper;
import com.example.bcp.mapper.StudentHomeworkMapper;
import com.example.bcp.mapper.StudentHomeworkMapper;
import com.example.bcp.service.NotificationService;
import com.example.bcp.service.StudentCourseService;
import com.example.bcp.service.StudentHomeworkService;
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

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private StudentHomeworkService studentHomeworkService;
    //返回某cid对应所有homework
    @GetMapping("/allCidHomework")
    public Result selectByCid(@RequestParam String cid,HttpServletRequest request){
        String userNo = request.getAttribute("username").toString();
        List<Homework> homeworks = homeworkMapper.selectByCid(cid);
        Map<String,Object> responseData = new HashMap<>();
        int index = 1;
        for(Homework homework:homeworks){
            Map<String,Object> hw = new HashMap<>();
            hw.put("homeworkNO",homework.getHomeworkNo());
            hw.put("homeworkDescription",homework.getHomeworkDescription());
            hw.put("homeworkStratTime",homework.getStartTime());
            hw.put("homeworkEndTime",homework.getEndTime());

            int studentNumbers = studentCourseService.selectByCid(cid).size();
            int submitNumbers = studentHomeworkService.selectByHomeworkNo(homework.getHomeworkNo()).size();
            String submitNumber = submitNumbers+"/"+studentNumbers;
            hw.put("submitNumber",submitNumber);

            StudentHomework sh = studentHomeworkService.selectByStudentNoAndHomeworkNo(userNo, homework.getHomeworkNo());
            if(sh == null){
                hw.put("status","未提交");
                hw.put("grade",null);
            }else {
                hw.put("status","已提交");
                hw.put("grade",sh.getSubmitGrade());
            }

            responseData.put("homework"+index,hw);
            index++;
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
        int submitGrade = Integer.parseInt(requestData.get("SubmitGrade").toString());
        String studentNo = (String)requestData.get("StudentNo");
        String homeworkNo = (String)requestData.get("HomeworkNo");
        String comment = requestData.get("comment").toString();
        try{
            studentHomeworkMapper.updateStudentHomeworkSubmitGrade(submitGrade,studentNo,homeworkNo,comment);
            return Result.success("成功批改");
        }catch (Exception e){
            return Result.success("批改失败");
        }

    }


}
