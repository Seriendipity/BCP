package com.example.bcp.mapper;

import com.example.bcp.entity.Homework;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface HomeworkMapper {
    @Select("Select * from Homework")
    List<Homework> selectAllHomework();

    @Select("Select * from Homework Where Cid = #{Cid}")
    List<Homework> selectByCid(String Cid);

    @Select("Select * from Homework Where Cid = #{Cid} and isVisible = 1")
    List<Homework> selectByCidAndVisible(String Cid);
    @Insert("Insert into Homework(HomeworkNo,Cid,HomeworkDescription,StartTime,EndTime,HomeworkGrade)" +
            " values(#{HomeworkNo},#{Cid},#{HomeworkDescription},#{StartTime},#{EndTime},#{HomeworkGrade})")
    void insertHomework(String HomeworkNo, String Cid, String HomeworkDescription,
                        LocalDateTime StartTime , LocalDateTime EndTime , int HomeworkGrade);

    @Delete("Delete from Homework Where HomeworkNo = #{HomeworkNo}")
    void deleteHomework(String HomeworkNo);

    @Update("Update Homework Set EndTime = #{EndTime} Where HomeworkNo = #{HomeworkNo}")
    void updateHomeworkEndTime(LocalDateTime EndTime , String HomeworkNo);

    @Update("Update Homework Set HomeworkPath = #{HomeworkPath} Where HomeworkNo = #{HomeworkNo}")
    void updateHomeworkPath(String HomeworkPath,String HomeworkNo);

    @Update("Update Homework Set HomeworkDescription = #{HomeworkDescription} Where HomeworkNo = #{HomeworkNo}")
    void updateHomeworkDescription(String HomeworkDescription,String HomeworkNo);

    @Update("Update Homework Set HomeworkGrade = #{HomeworkGrade} Where HomeworkNo = #{HomeworkNo}")
    void updateHomeworkGrade(int HomeworkGrade,String HomeworkNo);
}

