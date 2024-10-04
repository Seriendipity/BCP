package com.example.bcp.mapper;

import com.example.bcp.entity.Class;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface ClassMapper {
    @Select("Select * from Class")
    List<Class> selectAllClass();

    @Select("Select * from Class where ClassNo = #{ClassNo}")
    Class selectByClassNo(String ClassNo);

    @Update("Update Class set StudentNumber = #{StudentNumber} where ClassNo = #{ClassNo}")
    void updateStudentNumber(String ClassNo , int StudentNumber);

}
