package com.example.bcp.mapper;

import com.example.bcp.entity.CourseResource;
import com.example.bcp.service.CourseResourceService;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseResourceMapper {
    @Select("Select * from CourseResource")
    List<CourseResource> selectAllCourseResource();

    @Select("Select * from CourseResource Where Cid = #{Cid}")
    List<CourseResource> selectByCid(String Cid);

    @Select("Select * from CourseResource Where CourseResourceNo = #{CourseResourceNo}")
    CourseResource selectByCourseResourceNo(String CourseResourceNo);

    @Insert("Insert into CourseResource(CourseResourceNo,Cid,ResourceType,CourseResourcePath)" +
            " values(#{CourseResourceNo},#{Cid},#{ResourceType},#{CourseResourcePath})")
    void insertCourseResource(String CourseResourceNo,String Cid,
                              String ResourceType,String CourseResourcePath);

    @Delete("Delete from CourseResource Where CourseResourceNo = #{CourseResourceNo}")
    void deleteCourseResource(String CourseResourceNo);

    @Update("Update CourseResource Set CourseResourcePath = #{CourseResourcePath} Where CourseResourceNo = #{CourseResourceNo}")
    void updateCourseResource(String CourseResourcePath,String CourseResourceNo);

    @Select("select MAX(CAST(CourseResourceNo AS UNSIGNED)) from CourseResource")
    int maxNo();


}
