package com.example.bcp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@IdClass(StudentNotificationId.class)
@Table(name = "StudentNotification")
public class StudentNotification {
    @Id
    @Column(name = "StudentNo" , length = 10)
    private String StudentNo;

    @Id
    @Column(name = "NotificationNo" , length = 10)
    private String NotificationNo;

    @Column(name = "NotificationDate")
    private LocalDateTime NotificationDate;

    public boolean isNotificationState() {
        return NotificationState;
    }

    public void setNotificationState(boolean notificationState) {
        NotificationState = notificationState;
    }

    @Column(name = "NotificationState")
    private boolean NotificationState = false;

    public String getStudentNo() {
        return StudentNo;
    }

    public void setStudentNo(String studentNo) {
        StudentNo = studentNo;
    }

    public String getNotificationNo() {
        return NotificationNo;
    }

    public void setNotificationNo(String notificationNo) {
        NotificationNo = notificationNo;
    }

    public LocalDateTime getNotificationDate() {
        return NotificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        NotificationDate = notificationDate;
    }

    public boolean getNotificationState() {
        return NotificationState;
    }

//    public void setNotificationState(boolean notificationState) {
//        NotificationState = notificationState;
//    }
}
