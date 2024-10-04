package com.example.bcp.mapper;

import com.example.bcp.entity.Course;
import com.example.bcp.service.CourseService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {
    @Select("Select * from Course")
    List<Course> selectAllCourse();

    @Select("Select * from Course Where CourseNo = #{CourseNo}")
    Course selectByCourseNo(String CourseNo);
}
