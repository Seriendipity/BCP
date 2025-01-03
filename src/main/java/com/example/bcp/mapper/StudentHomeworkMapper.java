package com.example.bcp.mapper;

import com.example.bcp.entity.Student;
import com.example.bcp.entity.StudentHomework;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface StudentHomeworkMapper {
    @Select("Select * from StudentHomework")
    List<StudentHomework> selectAllStudentHomework();

    @Select("Select * from StudentHomework Where StudentNo = #{StudentNo}")
    List<StudentHomework> selectByStudentNo(String StudentNo);

    @Select("Select * from StudentHomework Where isTeacherGrade = true and HomeworkNo = #{HomeworkNo}")
    List<StudentHomework> selectByIsTeacherAndHomeworkNo(String HomeworkNo);

    @Select("Select * from StudentHomework Where HomeworkNo = #{HomeworkNo}")
    List<StudentHomework> selectByHomeworkNo(String HomeworkNo);

    @Select("Select * from StudentHomework Where HomeworkNo = #{HomeworkNo} and SubmitGrade is not null and isTeacherGrade = 1")
    List<StudentHomework> selectCorrectingHomework(String HomeworkNo);

    @Select("Select * from StudentHomework Where StudentNo = #{StudentNo} and HomeworkNo = #{HomeworkNo}")
    StudentHomework selectByStudentNoAndHomeworkNo(String StudentNo, String HomeworkNo);

    @Insert("Insert into StudentHomework(StudentNo,HomeworkNo,SubmitDescription,SubmitPath,SubmitTime,SubmitGrade,Comment,CorrectTime)" +
            " values(#{StudentNo},#{HomeworkNo},#{SubmitDescription},#{SubmitPath},#{SubmitTime},null,#{Comment},Now())")
    void insertStudentHomework(String StudentNo, String HomeworkNo, String SubmitDescription,
                               String SubmitPath , LocalDateTime SubmitTime,String Comment);

    @Update("Update StudentHomework Set SubmitPath = #{SubmitPath}, SubmitTime = NOW() Where StudentNo = #{StudentNo} and HomeworkNo = #{HomeworkNo}")
    void updateStudentHomeworkPath(String SubmitPath , String StudentNo , String HomeworkNo);

    @Update("Update StudentHomework Set SubmitGrade = #{SubmitGrade} ,Comment = #{Comment} Where StudentNo = #{StudentNo} and HomeworkNo = #{HomeworkNo}")
    void updateStudentHomeworkSubmitGrade(int SubmitGrade,String StudentNo,String HomeworkNo,String Comment);

    @Update("Update StudentHomework Set isTeacherGrade= #{isTeacherGrade} Where StudentNo = #{StudentNo} and HomeworkNo = #{HomeworkNo}")
    void updateStudentHomeworkIsTeacherGrade(boolean isTeacherGrade, String StudentNo,String HomeworkNo);

    @Update("Update StudentHomework Set isVisible = 1 - isVisible Where HomeworkNo = #{HomeworkNo}")
    void updateVisible(String HomeworkNo);
}
