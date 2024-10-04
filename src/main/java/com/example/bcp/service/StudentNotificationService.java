package com.example.bcp.service;

import com.example.bcp.entity.Student;
import com.example.bcp.entity.StudentNotification;
import com.example.bcp.mapper.StudentNotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentNotificationService {
    @Autowired
    private StudentNotificationMapper studentNotificationMapper;

    public List<StudentNotification> selectAllStudentNotification(){
        return studentNotificationMapper.selectAllStudentNotification();
    }

    public List<StudentNotification> selectByStudentNo(String StudentNo){
        return studentNotificationMapper.selectByStudentNo(StudentNo);
    }

    public StudentNotification selectByStudentNoAndNotificationNo(String StudentNo , String NotificationNo){
        return studentNotificationMapper.selectByStudentNoAndNotificationNo(StudentNo,NotificationNo);
    }

    public List<StudentNotification> selectByNotificationState(String NotificationState){
        return studentNotificationMapper.selectByNotificationState(NotificationState);
    }

    public void insertStudentNotification(String StudentNo, String NotificationNo,
                                          LocalDateTime NotificationDate , String NotificationState){
        studentNotificationMapper.insertStudentNotification(StudentNo,NotificationNo,
                                            NotificationDate,NotificationState);
    }

    public void deleteStudentNotification(String StudentNo , String NotificationNo){
        studentNotificationMapper.deleteStudentNotification(StudentNo , NotificationNo);
    }
}
