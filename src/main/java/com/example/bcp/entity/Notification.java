package com.example.bcp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Notification")
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
