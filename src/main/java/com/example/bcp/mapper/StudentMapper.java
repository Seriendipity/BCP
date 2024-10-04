package com.example.bcp.mapper;

import com.example.bcp.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("Select * from Student")
    List<Student> selectAllStudent();

    @Select("Select * from Student Where StudentNo = #{StudentNo}")
    Student selectByStudentNo(String StudentNo);

    @Update("Update Student Set TotalCredit = #{TotalCredit} Where StudentNo = #{StudentNo}")
    void updateStudentCredit(short TotalCredit , String StudentNo);

    @Update("Update Student Set Password = #{Password} Where StudentNo = #{StudentNo}")
    void updateStudentPassword(String Password , String StudentNo);
}
