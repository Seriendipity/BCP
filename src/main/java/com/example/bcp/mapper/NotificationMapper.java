package com.example.bcp.mapper;

import com.example.bcp.entity.Notification;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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

    @Insert("Insert into Notification(NotificationNo,Cid,SendNo,NotificationInformation)" +
            " values(#{NotificationNo},#{Cid},#{SendNo},#{NotificationInformation})")
    void insertNotification(String NotificationNo,String Cid,
                            String SendNo,String NotificationInformation);

    @Delete("Delete From Notification Where NotificationNo = #{NotificationNo}")
    void deleteNotification(String NotificationNo);
}
