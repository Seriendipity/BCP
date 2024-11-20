package com.example.bcp.mapper;

import com.example.bcp.entity.StudentNotification;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface StudentNotificationMapper {
    @Select("Select * from StudentNotification")
    List<StudentNotification> selectAllStudentNotification();

    @Select("Select * from StudentNotification Where StudentNo = #{StudentNo}")
    List<StudentNotification> selectByStudentNo(String StudentNo);

    @Select("Select * from StudentNotification Where StudentNo = #{StudentNo} and NotificationNo = #{NotificationNo}")
    StudentNotification selectByStudentNoAndNotificationNo(String StudentNo , String NotificationNo);

    @Select("Select * from StudentNotification Where NotificationState = #{NotificationState}")
    List<StudentNotification> selectByNotificationState(String NotificationState);

    @Insert("Insert into StudentNotification(StudentNo,NotificationNo,NotificationDate,NotificationState)" +
            " values(#{StudentNo} , #{NotificationNo} , NOW(),false)")
    void insertStudentNotification(String StudentNo, String NotificationNo);

    @Delete("Delete from StudentNotification Where StudentNo = #{StudentNo} and NotificationNo = #{NotificationNo}")
    void deleteStudentNotification(String StudentNo , String NotificationNo);


    @Update("Update StudentNotification set NotificationState = true where StudentNo = #{studentNo} and NotificationNo = #{notificationNo}")
    int updateNotificationState(String studentNo, String notificationNo);

}
