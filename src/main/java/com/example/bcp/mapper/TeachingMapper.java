package com.example.bcp.mapper;

import com.example.bcp.entity.Teaching;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.awt.*;
import java.util.List;

/**
 * 查询某门课程的具体信息可以通过先查询某门课程的全部信息
 * 再调用get方法获得
 */
@Mapper
public interface TeachingMapper {
    @Select("Select * from Teaching")
    List<Teaching> selectAllTeaching();

    @Select("Select * from Teaching where TeacherNo = #{TeacherNo}")
    List<Teaching> selectByTeacherNo(String TeacherNo);

    @Select("Select * from Teaching Where CourseNo = #{CourseNo}")
    List<Teaching> selectByCourseNo(String CourseNo);

    @Select("Select * from Teaching Where Cid = #{Cid}")
    Teaching selectByCid(String Cid);

    @Select("Select * from Teaching Where TeacherNo = #{TeacherNo} and CourseNo = #{CourseNo}")
    List<Teaching> selectByTeacherNoAndCourseNo(String TeacherNo , String CourseNo);

}
