package com.example.bcp.mapper;

import com.example.bcp.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Select("Select * from Teacher")
    List<Teacher> selectAllTeacher();

    @Select("Select * from Teacher Where TeacherNo = #{TeacherNo}")
    Teacher selectByTeacherNo(String TeacherNo);

    @Update("Update Teacher Set TeacherTitle = #{TeacherTitle} Where TeacherNo = #{TeacherNo}")
    void  updateTeacherTitle(String TeacherTitle , String TeacherNo);

    @Update("Update Teacher Set Password = #{Password} Where TeacherNo = #{TeacherNo}")
    void updateTeacherPassword(String Password , String TeacherNo);

    @Update("Update Teacher Set PicturePath = #{PicturePath} Where TeacherNo = #{TeacherNo}")
    void updateTeacherPicturePath(String PicturePath , String TeacherNo);
}
