package com.example.bcp.controller;

import com.example.bcp.entity.*;
import com.example.bcp.service.*;
import jakarta.persistence.Entity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.bcp.service.PeerReviewAssignmentService;
import com.example.bcp.mapper.StudentHomeworkMapper;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private HomeworkService homeworkService;

    //同伴打分以及写评论
    @PostMapping(value = "/updatePeerGrade",consumes = "multipart/form-data")
    public Result updatePeerGrade(@RequestParam String grade,
                                  @RequestParam String comment,
                                  @RequestParam String homeworkNo,
                                  @RequestParam String revieweeNo,
                                  HttpServletRequest request) {
        String userNo = request.getAttribute("username").toString();
        int grades = Integer.parseInt(grade);

        try {
            peerReviewAssignmentService.updateGradeAndComment(grades, comment, revieweeNo, userNo, homeworkNo);
            peerReviewAssignmentService.updateReviewStatus(revieweeNo, userNo, homeworkNo, true);
            System.out.println("success");
            return Result.success("同伴打分成功");
        } catch (Exception e) {
            return Result.error("打分失败");
        }
    }

    //教师打分（与studentHomework中一致？
    @PostMapping("/updateGrade")
    public Result updateGrade(@RequestParam String comment,
                              @RequestParam String studentNo,
                              @RequestParam String grade,
                              @RequestParam String homeworkNo,
                              HttpServletRequest request) {
        int grades = Integer.parseInt(grade);
        try {
            studentHomeworkService.updateStudentHomeworkSubmitGrade(grades, studentNo, homeworkNo, comment);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (userNo.startsWith("S")) {
            try {
                List<PeerReviewAssignment> peerReviewAssignments = peerReviewAssignmentService.selectByReviewerAndHomework(userNo, homeworkNo);
                Map<String, Object> responseData = new HashMap<>();
                int sequence = 1;
                for (PeerReviewAssignment pa : peerReviewAssignments) {
                    Map<String, Object> pa1 = new HashMap<>();
                    pa1.put("sequence", sequence);
                    pa1.put("homeworkNo", pa.getHomeworkNo());
                    pa1.put("reviewee",pa.getRevieweeNo());
                    StudentHomework sh = studentHomeworkService.selectByStudentNoAndHomeworkNo(pa.getRevieweeNo(),pa.getHomeworkNo());
                    pa1.put("homeworkPath",sh.getSubmitPath());
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
                    Homework homework = homeworkService.selectByHomeworkNo(homeworkNo);
                    if (homework == null) {
                        System.out.println("No homework found for homeworkNo: " + homeworkNo);
                        return Result.error("找不到作业");
                    }
                    List<StudentHomework> studentHomeworks;
                    if(homework.getPeerReview()) {
                        studentHomeworks = studentHomeworkService.selectByIsTeacherAndHomeworkNo(homeworkNo);
                    }else{
                        studentHomeworks = studentHomeworkService.selectByHomeworkNo(homeworkNo);
                    }
                    System.out.println("success");
                    Map<String, Object> responseData = new HashMap<>();
                    int sequence = 1;
                    boolean status;
                    for (StudentHomework pa : studentHomeworks) {
                        Map<String, Object> pa1 = new HashMap<>();
                        pa1.put("sequence", sequence);
                        pa1.put("homeworkNo", pa.getHomeworkNo());
                        pa1.put("studentNo", pa.getStudentNo());
                        Student student = studentService.selectByStudentNo(pa.getStudentNo());
                        pa1.put("studentName", student.getStudentName());//姓名
                        pa1.put("homeworkPath",pa.getSubmitPath());
                        pa1.put("path", pa.getSubmitPath());
                        pa1.put("grade",pa.getSubmitGrade()==0?"教师未批改":pa.getSubmitGrade());
                        pa1.put("status",pa.getSubmitGrade()==0?false:true);
                        LocalDateTime time = pa.getCorrectTime();
                        pa1.put("submitTime",pa.getSubmitTime().format(formatter));
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
                if(pa.getReviewStatus() == false){//改作业学生没有评
                    continue;
                }
                sumBias += sh.getSubmitGrade() - pa.getGrade();
            }
            size += assignments.size();
        }
        //计算最终成绩
        for (StudentHomework sh : studentHomeworkService.selectByHomeworkNo(homeworkNo)) {
            if (sh.getIsTeacherGrade() == false) {//没有教师评分
                int grade = 0;
                for (PeerReviewAssignment pa : peerReviewAssignmentService.selectByRevieweeNo(sh.getStudentNo())) {
                    if(pa.getReviewStatus() == false){
                        continue;
                    }
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


    //返回没有学生互评的作业交给教师评

}