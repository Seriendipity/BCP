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
import java.util.*;

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
    public Result sendNotification(@RequestBody Map<String, Object> requestData,HttpServletRequest request) {
//        String noteNo = (String) requestData.get("NoteNo");
//        boolean authority = (Boolean) requestData.get("authority");
        String cid = (String) requestData.get("cid");
        String notificationInformation = (String) requestData.get("notificationInformation");
        String notificationTitle = (String) requestData.get("notificationTitle");
        String users;
        if(requestData.get("users") == null){
            users = null;
        }else{
            users = requestData.get("users").toString();
        }

        String sendNo = request.getAttribute("username").toString();
        int size = notificationService.selectAllNotification().size()+1;
        System.out.println(size);
        String notificationNo = "N"+size;

        while (true){
            if(notificationService.selectByNotificationNo(notificationNo) != null){
                size += 1;
                notificationNo = "N" +size;
            }else{
                break;
            }
        }

        notificationService.insertNotification(
                notificationNo,
                cid,
                sendNo,
                notificationInformation,
                notificationTitle
        );

        // 插入学生通知记录
        try{
            if(users != null){
                if(users.contains("all")){
                    List<String > studentNos = studentCourseService.selectAllStudentNo(cid);
                    for (String studentNo : studentNos) {
                        if(studentNotificationService.selectByStudentNoAndNotificationNo(studentNo,notificationNo) != null) continue;
                        studentNotificationService.insertStudentNotification(
                                studentNo,
                                notificationNo
                        );
                    }
                }else{
                    List<String> userList = new ArrayList<>(Arrays.asList(users.split(",")));
                    for(String studentNo : userList){
                        if(studentNotificationService.selectByStudentNoAndNotificationNo(studentNo,notificationNo) != null) continue;
                        studentNotificationService.insertStudentNotification(
                                studentNo,
                                notificationNo
                        );
                    }
                }
                return Result.success("通知发送成功");
            }else{
                return Result.success("仅发出通知，还未通知给学生");
            }
        }catch (Exception e){
            return Result.error("通知发送失败，请重新发送");
        }
    }

    /**
     *  教师或助教将还未转发给学生的通知进行转发
     */
    @PostMapping("/send_toStudent")
    public Result sendNotificationToStudent(@RequestBody  Map<String,Object> requestData,HttpServletRequest request){
        String notificationNo = requestData.get("notificationNo").toString();
        String users = requestData.get("users").toString();
        Notification n = notificationService.selectByNotificationNo(notificationNo);
        String cid = n.getCid();

        // 插入学生通知记录
        try{
            if(users.contains("all")){
                List<String > studentNos = studentCourseService.selectAllStudentNo(cid);
                for (String studentNo : studentNos) {
                    if(studentNotificationService.selectByStudentNoAndNotificationNo(studentNo,notificationNo) != null) continue;
                    studentNotificationService.insertStudentNotification(
                            studentNo,
                            notificationNo
                    );
                }
            }else{
                List<String> userList = new ArrayList<>(Arrays.asList(users.split(",")));
                for(String studentNo : userList){
                    if(studentNotificationService.selectByStudentNoAndNotificationNo(studentNo,notificationNo) != null) continue;
                    studentNotificationService.insertStudentNotification(
                            studentNo,
                            notificationNo
                    );
                }
            }
            return Result.success("通知发送成功");
        }catch (Exception e){
            return Result.error("通知发送失败，请重新发送");
        }
    }

    // 教师/助教修改通知
    @PostMapping("/update")
    public Result updateNotification(@RequestBody Map<String, Object> requestData) {
//        @PathVariable String notificationNo,
//        @RequestParam String notificationInformation,
//        @RequestParam String notificationTitle
        String notificationNo = (String) requestData.get("notificationNo");
        String notificationInformation= (String) requestData.get("notificationInformation");
        String notificationTitle= (String) requestData.get("notificationTitle");

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("notificationNo",notificationNo);
        notificationService.updateNotificationInformation(
                notificationNo,
                notificationInformation,
                notificationTitle
        );
        responseData.put("notificationInformation",notificationInformation);
        responseData.put("notificationTitle",notificationTitle);
        return Result.success(responseData);
    }

    // 学生打开通知，更新状态
    @PostMapping("/student/updateState")
    public Result updateStudentNotificationState(@RequestParam String notificationNo , HttpServletRequest request) {
//        @PathVariable String studentNo,
//        @PathVariable String notificationNo
        String username = request.getAttribute("username").toString();
        studentNotificationService.updateStudentNotificationState(username, notificationNo);
        return Result.success();
    }

    // 学生查看所有通知(弃用，新版见下）
    @GetMapping("/student/{studentNo}")
    public List<StudentNotification> getStudentNotifications(@PathVariable String studentNo) {
        return studentNotificationService.selectByStudentNo(studentNo);
    }

    /**
     *  学生查看的是这门课程与自己有关的所有通知
     *  教师查看的是这门课程的所有通知
     */
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
                    notification.put("notificationId",n.getNotificationNo());
                    responseData.put("notification"+i,notification);
                    i++;
                }
            }
           return Result.success(responseData);
        }
        if(username.startsWith("T") || username.startsWith("A")){
            System.out.println("T or A users");
            List<Notification> notifications = notificationService.selectByCid(cid);
            int i = 1;
            for(Notification n : notifications){
                Map<String,Object> notification = new HashMap<>();
                notification.put("index",i);
                notification.put("notificationInfo",n.getNotificationInformation());
                notification.put("notificationTitle",n.getNotificationTitle());
                notification.put("notificationPostingTime",n.getPostingTime());
                notification.put("sendNo",n.getSendNo());
                notification.put("notificationId",n.getNotificationNo());
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

    @PostMapping("/change_status")
    public Result changeNotificationStatus(@RequestBody Map<String,String> responseData,HttpServletRequest request){
        String username = request.getAttribute("username").toString();
        String NotificationId = responseData.get("notificationId");
        System.out.println("-----------------");
        System.out.println(NotificationId);
        StudentNotification sn = studentNotificationService.selectByStudentNoAndNotificationNo(username,NotificationId);
        if(!sn.isNotificationState()){
            studentNotificationService.updateStudentNotificationState(username,NotificationId);
            responseData.put("notificationState","已读");
        }
        return Result.success(responseData);
    }

    // 查看所有通知
//    @GetMapping("/")
//    public List<Notification> getAllNotifications() {
//        return notificationService.selectAllNotification();
//    }
}
