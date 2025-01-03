package com.example.bcp.mapper;

import com.example.bcp.entity.Homework;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface HomeworkMapper {
    @Select("Select * from Homework")
    List<Homework> selectAllHomework();

    @Select("Select * from Homework Where HomeworkNo = #{HomeworkNo}")
    Homework selectByHomeworkNo(String HomeworkNo);

    @Select("Select * from Homework Where Cid = #{Cid}")
    List<Homework> selectByCid(String Cid);

    @Select("Select * from Homework Where Cid = #{Cid} and isVisible = 1")
    List<Homework> selectByCidAndVisible(String Cid);

    @Insert("Insert into homework(Cid,HomeworkNo,HomeworkDescription,StartTime,EndTime,HomeworkGrade,homeworkInfo,HomeworkPath)" +
            " values(#{Cid},#{HomeworkNo},#{HomeworkDescription},#{StartTime},#{EndTime},#{HomeworkGrade},#{homeworkInfo},#{HomeworkPath})")
    void insertHomework(String Cid, String HomeworkNo, String HomeworkDescription,
                        LocalDateTime StartTime, LocalDateTime EndTime, int HomeworkGrade,
                        String homeworkInfo, String HomeworkPath);

    @Delete("Delete from Homework Where HomeworkNo = #{HomeworkNo}")
    void deleteHomework(String HomeworkNo);

    @Update("Update Homework Set EndTime = #{EndTime} Where HomeworkNo = #{HomeworkNo}")
    void updateHomeworkEndTime(LocalDateTime EndTime, String HomeworkNo);

    @Update("Update Homework Set StartTime = #{StartTime} Where HomeworkNo = #{HomeworkNo}")
    void updateHomeworkStartTime(LocalDateTime StartTime, String HomeworkNo);

    @Update("Update Homework Set isPeerReview= #{isPeerReview} Where HomeworkNo = #{HomeworkNo}")
    void updateIsPeerReview(boolean isPeerReview, String HomeworkNo);

    @Update("Update Homework Set HomeworkPath = #{HomeworkPath} Where HomeworkNo = #{HomeworkNo}")
    void updateHomeworkPath(String HomeworkPath, String HomeworkNo);

    @Update("Update Homework Set HomeworkDescription = #{HomeworkDescription} Where HomeworkNo = #{HomeworkNo}")
    void updateHomeworkDescription(String HomeworkDescription, String HomeworkNo);

    @Update("Update Homework Set HomeworkGrade = #{HomeworkGrade} Where HomeworkNo = #{HomeworkNo}")
    void updateHomeworkGrade(int HomeworkGrade, String HomeworkNo);

    @Update("Update Homework Set homeworkInfo = #{homeworkInfo} Where HomeworkNo = #{HomeworkNo}")
    void updateHomeworkInfo(String homeworkInfo,String HomeworkNo);

    @Update("Update Homework Set isVisible = 1 - isVisible Where HomeworkNo = #{HomeworkNo}")
    void updateHomeworkIsVisible(String HomeworkNo);
}

