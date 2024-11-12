package com.example.bcp.controller;

import com.example.bcp.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.bcp.service.PeerReviewAssignmentService;
import com.example.bcp.service.PeerReviewAssignmentService;
import com.example.bcp.service.StudentHomeworkService;
import com.example.bcp.mapper.StudentHomeworkMapper;
import org.springframework.web.bind.annotation.*;

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


    //同伴打分以及写评论
    @PostMapping("/updatePeerGrade")
    public Result updatePeerGrade(@RequestBody Map<String, Object> requestData,HttpServletRequest request) {
        String userNo = request.getAttribute("username").toString();
        int grade = Integer.parseInt(requestData.get("grade").toString());
        String comment = (String) requestData.get("comment");
        String homeworkNo = (String) requestData.get("homeworkNo");
        String revieweeNo = (String) requestData.get("revieweeNo");

        try {
            peerReviewAssignmentService.updateGradeAndComment(grade,comment,revieweeNo,userNo, homeworkNo);
            peerReviewAssignmentService.updateReviewStatus(revieweeNo,userNo,homeworkNo,true);
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
            studentHomeworkService.updateStudentHomeworkSubmitGrade(grade,studentNo,homeworkNo,comment);
            System.out.println("success");
            return Result.success("教师成功");
        } catch (Exception e) {
            return Result.success("打分失败");
        }
    }

    //展示某学生所有互评任务
    @GetMapping("/studentPeerAssignment")
    public Result assignHomework(HttpServletRequest request) {
        String userNo = request.getAttribute("username").toString();
        try {
            List<PeerReviewAssignment> peerReviewAssignments = peerReviewAssignmentService.selectByReviewerNo(userNo);
            Map<String,Object> responseData = new HashMap<>();
            int sequence = 1;
            for(PeerReviewAssignment pa:peerReviewAssignments){
                Map<String,Object> pa1 = new HashMap<>();
                pa1.put("sequence",sequence);
                pa1.put("homeworkNO",pa.getHomeworkNo());
                pa1.put("revieStatus",pa.getReviewStatus());
                pa1.put("startTime",pa.getStartTime());
                pa1.put("endTime",pa.getEndTime());
                responseData.put("peerReview" + sequence, pa1);
                sequence++;
            }

            return Result.success(responseData);
        } catch (Exception e) {
            return Result.error("失败");
        }
    }

    @PostMapping("/finalGrade")
    public Result calculateGrade(@RequestParam String homeworkNo, HttpServletRequest request) {
        //获取HomeworkNo
//        String homeworkNo = requestData.get("homeworkNo").toString();
        //需要：算本homeworkNo下，每个studentNo的成绩
        int sumBias = 0;
        int size = 0;
        //本homeworkNo有教师评分的studentHomework
        List<StudentHomework> studentHomeworks = studentHomeworkService.selectByIsTeacherAndHomeworkNo(homeworkNo);
        for(StudentHomework sh:studentHomeworks){
            String studentNo = sh.getStudentNo();
            List<PeerReviewAssignment> assignments = peerReviewAssignmentService.selectByRevieweeAndHomework(studentNo, homeworkNo);
            for(PeerReviewAssignment pa:assignments){
                sumBias+= sh.getSubmitGrade() - pa.getGrade();
            }
            size += assignments.size();
        }
        //计算最终成绩
        for(StudentHomework sh: studentHomeworkService.selectByHomeworkNo(homeworkNo)){
            if(sh.getIsTeacherGrade() == false){//没有教师评分
                int grade=0;
                for(PeerReviewAssignment pa: peerReviewAssignmentService.selectByRevieweeNo(sh.getStudentNo())){
                    grade += pa.getGrade();
                }
                grade = grade/peerReviewAssignmentService.selectByRevieweeNo(sh.getStudentNo()).size();
                studentHomeworkService.updateStudentHomeworkSubmitGrade(grade,sh.getStudentNo(),sh.getHomeworkNo(),null);
            }
        }
        return Result.success();
    }
}