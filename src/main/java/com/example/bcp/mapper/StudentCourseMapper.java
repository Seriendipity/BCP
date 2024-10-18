package com.example.bcp.mapper;

import com.example.bcp.entity.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentCourseMapper {
    @Select("Select * from StudentCourse")
    List<StudentCourse> selectAllStudentCourse();
    @Select("Select StudentNo from StudentCourse Where Cid = #{Cid}")
    List<String> selectAllStudentNo(String cid);
    @Select("Select * from StudentCourse where StudentNo = #{StudentNo}")
    List<StudentCourse> selectByStudentNo(String StudentNo);

    @Select("Select * from StudentCourse Where Cid = #{Cid}")
    List<StudentCourse> selectByCid(String Cid);
}
