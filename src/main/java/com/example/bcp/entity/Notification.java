package com.example.bcp.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notification")
@Schema(name = "Notification", description = "$!{这是通知}")
public class Notification {
    @Id
    @Column(name = "NotificationNo" , length = 10)
    private String NotificationNo;

    @Column(name = "Cid" , length = 10)
    private String Cid;

    @Column(name = "SendNo" , length = 10)
    private String SendNo;

    @Column(name = "NotificationInformation" , columnDefinition = "text")
    private String NotificationInformation;

    @Column(name = "NotificationTitle",length = 30)
    private String NotificationTitle;

    @Column(name = "PostingTime")
    private LocalDateTime PostingTime;

    public LocalDateTime getPostingTime() {
        return PostingTime;
    }

    public void setPostingTime(LocalDateTime postingTime) {
        PostingTime = postingTime;
    }

    public String getNotificationTitle() {
        return NotificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        NotificationTitle = notificationTitle;
    }

    public String getNotificationInformation() {
        return NotificationInformation;
    }

    public void setNotificationInformation(String notificationInformation) {
        NotificationInformation = notificationInformation;
    }

    public String getNotificationNo() {
        return NotificationNo;
    }

    public void setNotificationNo(String notificationNo) {
        NotificationNo = notificationNo;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getSendNo() {
        return SendNo;
    }

    public void setSendNo(String sendNo) {
        SendNo = sendNo;
    }
}
