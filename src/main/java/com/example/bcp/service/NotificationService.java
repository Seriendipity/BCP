package com.example.bcp.service;

import com.example.bcp.entity.Notification;
import com.example.bcp.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    public List<Notification> selectAllNotification(){
        return notificationMapper.selectAllNotification();
    }

    public List<Notification> selectByNotificationInformation(String keyword){
        return notificationMapper.selectByNotificationInformation(keyword);
    }

    public List<Notification> selectByCid(String Cid){
        return notificationMapper.selectByCid(Cid);
    }

    public List<Notification> selectBySendNo(String SendNo){
        return notificationMapper.selectBySendNo(SendNo);
    }

    public Notification selectByNotificationNo(String NotificationNo){
        return notificationMapper.selectByNotificationNo(NotificationNo);
    }

    public void insertNotification(String NotificationNo,String Cid,
                                   String SendNo,String NotificationInformation , String NotificationTitle){
        notificationMapper.insertNotification(NotificationNo,Cid,SendNo,NotificationInformation,NotificationTitle);
    }

    public void updateNotificationInformation(String NotificationNo,String NotificationInformation,String NotificationTitle){notificationMapper.updateNotificationInformation(NotificationNo,NotificationInformation,NotificationTitle);}

    public void deleteNotification(String NotificationNo){
        notificationMapper.deleteNotification(NotificationNo);
    }
}
