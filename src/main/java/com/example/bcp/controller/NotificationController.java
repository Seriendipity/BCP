package com.example.bcp.controller;

import com.example.bcp.entity.StudentNotification;
import com.example.bcp.service.NotificationService;
import com.example.bcp.service.StudentNotificationService;
import com.example.bcp.service.StudentCourseService;
import com.example.bcp.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.bcp.entity.Result;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private StudentNotificationService studentNotificationService;

    @Autowired
    private StudentCourseService studentCourseService;

    // 教师/助教发送通知
    @PostMapping("/send")
    public void sendNotification(@RequestParam String cid,
                                 @RequestParam String sendNo,
                                 @RequestBody String notificationInformation) {

        int size = notificationService.selectAllNotification().size()+1;
        String notificationNo = "N"+size;

        notificationService.insertNotification(
                notificationNo,
                cid,
                sendNo,
                notificationInformation
        );

        // 插入学生通知记录
        List<String > studentNos = studentCourseService.selectAllStudentNo(cid);
        for (String studentNo : studentNos) {
            studentNotificationService.insertStudentNotification(
                    studentNo,
                    notificationNo,
                    LocalDateTime.now()
            );
        }
    }

    // 教师/助教修改通知
    @PutMapping("/update/{notificationNo}")
    public void updateNotification(@PathVariable String notificationNo,
                                   @RequestBody String notificationInformation) {
        notificationService.updateNotificationInformation(
                notificationNo,
                notificationInformation
        );
    }

    // 学生打开通知，更新状态
    @PutMapping("/student/updateState/{studentNo}/{notificationNo}")
    public void updateStudentNotificationState(@PathVariable String studentNo,
                                               @PathVariable String notificationNo) {
        studentNotificationService.updateStudentNotificationState(studentNo, notificationNo);
    }

    // 学生查看所有通知
    @GetMapping("/student/{studentNo}")
    public List<StudentNotification> getStudentNotifications(@PathVariable String studentNo) {
        return studentNotificationService.selectByStudentNo(studentNo);
    }

    // 教师/助教查看自己发送的通知
    @GetMapping("/teacher/sendNo/{sendNo}")
    public List<Notification> getNotificationsBySendNo(@PathVariable String sendNo) {
        return notificationService.selectBySendNo(sendNo);
    }

    // 查看所有通知
//    @GetMapping("/")
//    public List<Notification> getAllNotifications() {
//        return notificationService.selectAllNotification();
//    }
}
