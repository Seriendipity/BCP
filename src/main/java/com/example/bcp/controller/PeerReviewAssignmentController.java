package com.example.bcp.controller;

import com.example.bcp.entity.*;
import com.example.bcp.service.StudentService;
import jakarta.persistence.Entity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.bcp.service.PeerReviewAssignmentService;
import com.example.bcp.service.PeerReviewAssignmentService;
import com.example.bcp.service.StudentHomeworkService;
import com.example.bcp.mapper.StudentHomeworkMapper;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/peerAssignment")
public class PeerReviewAssignmentController {
    @Autowired
    private PeerReviewAssignmentService peerReviewAssignmentService;
    @Autowired
    private StudentHomeworkService studentHomeworkService;

    @Autowired
    private StudentService studentService;

    //同伴打分以及写评论
    @PostMapping("/updatePeerGrade")
    public Result updatePeerGrade(@RequestBody Map<String, Object> requestData, HttpServletRequest request) {
        String userNo = request.getAttribute("username").toString();
        int grade = Integer.parseInt(requestData.get("grade").toString());
        String comment = (String) requestData.get("comment");
        String homeworkNo = (String) requestData.get("homeworkNo");
        String revieweeNo = (String) requestData.get("revieweeNo");

        try {
            peerReviewAssignmentService.updateGradeAndComment(grade, comment, revieweeNo, userNo, homeworkNo);
            peerReviewAssignmentService.updateReviewStatus(revieweeNo, userNo, homeworkNo, true);
            System.out.println("success");
            return Result.success("同伴打分成功");
        } catch (Exception e) {
            return Result.error("打分失败");
        }
    }

    //教师打分（与studentHomework中一致？
    @PostMapping("/updateGrade")
    public Result updateGrade(@RequestBody Map<String, Object> requestData, HttpServletRequest request) {
        int grade = Integer.parseInt(requestData.get("grade").toString());
        String comment = (String) requestData.get("comment");
        String studentNo = (String) requestData.get("studentNo");
        String homeworkNo = (String) requestData.get("homeworkNo");
        try {
            studentHomeworkService.updateStudentHomeworkSubmitGrade(grade, studentNo, homeworkNo, comment);
            System.out.println("success");
            return Result.success("教师成功");
        } catch (Exception e) {
            return Result.success("打分失败");
        }
    }

    //展示某学生/老师所有互评任务
    @GetMapping("/studentPeerAssignment")
    public Result assignHomework(@RequestParam String homeworkNo, HttpServletRequest request) {
        String userNo = request.getAttribute("username").toString();
        if (userNo.startsWith("S")) {
            try {
                List<PeerReviewAssignment> peerReviewAssignments = peerReviewAssignmentService.selectByReviewerAndHomework(userNo, homeworkNo);
                Map<String, Object> responseData = new HashMap<>();
                int sequence = 1;
                for (PeerReviewAssignment pa : peerReviewAssignments) {
                    Map<String, Object> pa1 = new HashMap<>();
                    pa1.put("sequence", sequence);
                    pa1.put("homeworkNo", pa.getHomeworkNo());
                    pa1.put("reviewStatus", pa.getReviewStatus());
                    pa1.put("startTime", pa.getStartTime());
                    pa1.put("endTime", pa.getEndTime());
                    responseData.put("peerReview" + sequence, pa1);
                    sequence++;
                }
                return Result.success(responseData);
            } catch (Exception e) {
                return Result.error("失败");
            }
        } else if (userNo.startsWith("T")) {
            try {
                List<StudentHomework> studentHomeworks = studentHomeworkService.selectByIsTeacherAndHomeworkNo(homeworkNo);
                Map<String, Object> responseData = new HashMap<>();
                int sequence = 1;
                for (StudentHomework pa : studentHomeworks) {
                    Map<String, Object> pa1 = new HashMap<>();
                    pa1.put("sequence", sequence);
                    pa1.put("homeworkNo", pa.getHomeworkNo());
                    pa1.put("studentNo", pa.getStudentNo());
                    pa1.put("path", pa.getSubmitPath());
                    responseData.put("studentHomework" + sequence, pa1);
                    sequence++;
                }
                return Result.success(responseData);
            } catch (Exception e) {
                return Result.error("失败");
            }
        }else{
            return Result.error("失败");
        }

    }


    //计算学生成绩
    @GetMapping("/finalGrade")
    public Result calculateGrade(@RequestParam String homeworkNo, HttpServletRequest request) {
        //获取HomeworkNo
//        String homeworkNo = requestData.get("homeworkNo").toString();
        //需要：算本homeworkNo下，每个studentNo的成绩
        int sumBias = 0;
        int size = 0;
        //本homeworkNo有教师评分的studentHomework
        List<StudentHomework> studentHomeworks = studentHomeworkService.selectByIsTeacherAndHomeworkNo(homeworkNo);
        for (StudentHomework sh : studentHomeworks) {
            String studentNo = sh.getStudentNo();
            List<PeerReviewAssignment> assignments = peerReviewAssignmentService.selectByRevieweeAndHomework(studentNo, homeworkNo);
            for (PeerReviewAssignment pa : assignments) {
                sumBias += sh.getSubmitGrade() - pa.getGrade();
            }
            size += assignments.size();
        }
        //计算最终成绩
        for (StudentHomework sh : studentHomeworkService.selectByHomeworkNo(homeworkNo)) {
            if (sh.getIsTeacherGrade() == false) {//没有教师评分
                int grade = 0;
                for (PeerReviewAssignment pa : peerReviewAssignmentService.selectByRevieweeNo(sh.getStudentNo())) {
                    grade += pa.getGrade();
                }
                grade = grade / peerReviewAssignmentService.selectByRevieweeNo(sh.getStudentNo()).size();
                studentHomeworkService.updateStudentHomeworkSubmitGrade(grade, sh.getStudentNo(), sh.getHomeworkNo(), null);
            }
        }
        Map<String, Object> responseData = new HashMap<>();
        int sequence = 1;
        for (StudentHomework st : studentHomeworks) {
            Map<String, Object> st1 = new HashMap<>();
            st1.put("sequence", sequence);
            st1.put("homeworkNO", st.getHomeworkNo());
            Student student = studentService.selectByStudentNo(st.getStudentNo());
            st1.put("studentNO", st.getStudentNo());
            st1.put("studentName", student.getStudentName());
            st1.put("grade", st.getSubmitGrade());
            st1.put("submitTime", st.getSubmitTime());
            responseData.put("peerReview" + sequence, st1);
            sequence++;
        }
        return Result.success(responseData);
    }

    //返回截止时间
    @GetMapping("/endTime")
    public Result getEndTime(@RequestParam String homeworkNo) {
        Map<String, Object> responsedata = new HashMap<>();
        try {
            List<PeerReviewAssignment> pa = peerReviewAssignmentService.selectByHomeworkNo(homeworkNo);
            LocalDateTime endTime = pa.get(0).getEndTime();
            responsedata.put("EndTime", endTime);
        } catch (Exception e) {
            responsedata.put("EndTime", "2023-11-09T16:23:03");
            Result.success(responsedata);
        }
        return Result.success(responsedata);
    }
}