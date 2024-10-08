package com.example.bcp.service;

import com.example.bcp.entity.CourseResource;
import com.example.bcp.mapper.CourseResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseResourceService {
    @Autowired
    private CourseResourceMapper courseResourceMapper;

    public List<CourseResource> selectAllCourseResource(){
        return courseResourceMapper.selectAllCourseResource();
    }

    public List<CourseResource> selectByCid(String Cid){
        return courseResourceMapper.selectByCid(Cid);
    }

    public void insertCourseResource(String CourseResourceNo,String Cid,
                                     String ResourceType,String CourseResourcePath){
        courseResourceMapper.insertCourseResource(CourseResourceNo,Cid,
                                            ResourceType,CourseResourcePath);
    }

    public void deleteCourseResource(String CourseResourceNo){
        courseResourceMapper.deleteCourseResource(CourseResourceNo);
    }

    public void updateCourseResource(String CourseResourcePath , String CourseResourceNo){
        courseResourceMapper.updateCourseResource(CourseResourcePath,CourseResourceNo);
    }
    public int maxNo(){
        return courseResourceMapper.maxNo();
    }

}
