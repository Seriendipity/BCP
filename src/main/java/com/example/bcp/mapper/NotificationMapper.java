package com.example.bcp.mapper;

import com.example.bcp.entity.Notification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Select("Select * from Notification")
    List<Notification> selectAllNotification();

    @Select("Select * from Notification where Cid = #{Cid}")
    List<Notification> selectByCid(String Cid);

    @Select("Select * from Notification Where SendNo = #{SendNo}")
    List<Notification> selectBySendNo(String SendNo);

    @Select("Select * from Notification Where NotificationNo = #{NotificationNo}")
    Notification selectByNotificationNo(String NotificationNo);

    @Insert("Insert into Notification(NotificationNo,Cid,SendNo,NotificationInformation,NotificationTitle,PostingTime)" +
            " values(#{NotificationNo},#{Cid},#{SendNo},#{NotificationInformation},#{NotificationTitle},NOW())")
    void insertNotification(String NotificationNo,String Cid,
                            String SendNo,String NotificationInformation,
                            String NotificationTitle);

    @Delete("Delete From Notification Where NotificationNo = #{NotificationNo}")
    void deleteNotification(String NotificationNo);

    @Select("Select * from Notification Where NotificationInformation LIKE CONCAT('%',#{keyword},'%')")
    List <Notification> selectByNotificationInformation(String keyword);

    @Update("Update Notification set  NotificationInformation = #{NotificationInformation},NotificationTitle =#{NotificationTitle} where NotificationNo = #{NotificationNo}")
    void updateNotificationInformation(String NotificationNo,String NotificationInformation,String NotificationTitle);
}
