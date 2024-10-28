package com.example.bcp.controller;

import com.example.bcp.entity.StudentNotification;
import com.example.bcp.entity.Teaching;
import com.example.bcp.service.NotificationService;
import com.example.bcp.service.StudentNotificationService;
import com.example.bcp.service.StudentCourseService;
import com.example.bcp.entity.Notification;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.bcp.entity.Result;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                                 @RequestBody String notificationInformation,
                                 @RequestBody String notificationTitle) {

        int size = notificationService.selectAllNotification().size()+1;
        String notificationNo = "N"+size;

        notificationService.insertNotification(
                notificationNo,
                cid,
                sendNo,
                notificationInformation,
                notificationTitle
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
                                   @RequestBody String notificationInformation,
                                   @RequestBody String notificationTitle) {
        notificationService.updateNotificationInformation(
                notificationNo,
                notificationInformation,
                notificationTitle
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
    @GetMapping("/getNotification")
    public Result getNotification(@RequestParam String cid , HttpServletRequest request){
        String username = request.getAttribute("username").toString();
        Map<String,Object> responseData = new HashMap<>();
        if (username.startsWith("S")){
            List<Notification> notifications = notificationService.selectByCid(cid);
            int i = 1;
            for(Notification n : notifications){
                Map<String,Object> notification = new HashMap<>();
                String notificationNo = n.getNotificationNo();
                StudentNotification se = studentNotificationService.selectByStudentNoAndNotificationNo(username,notificationNo);
                if(se != null){
                    notification.put("index",i);
                    notification.put("notificationInfo",n.getNotificationInformation());
                    notification.put("notificationTitle",n.getNotificationTitle());
                    notification.put("sendNo",n.getSendNo());
                    notification.put("notificationPostingTime",n.getPostingTime());
                    String state = "未读";
                    if(se.isNotificationState()) state = "已读";
                    notification.put("notificationState",state);
                    responseData.put("notification"+i,notification);
                    i++;
                }
            }
           return Result.success(responseData);
        }
        if(username.startsWith("T")){
            System.out.println("T users");
            List<Notification> notifications = notificationService.selectByCid(cid);
            int i = 1;
            for(Notification n : notifications){
                Map<String,Object> notification = new HashMap<>();
                notification.put("index",i);
                notification.put("notificationInfo",n.getNotificationInformation());
                notification.put("notificationTitle",n.getNotificationTitle());
                notification.put("notificationPostingTime",n.getPostingTime());
                notification.put("sendNo",n.getSendNo());
                responseData.put("Notification"+i,notification);
                i++;
            }
            return Result.success(responseData);
        }
        return Result.error("错误用户");
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
