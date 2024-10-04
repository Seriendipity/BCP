package com.example.bcp.service;

import com.example.bcp.entity.Course;
import com.example.bcp.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseMapper courseMapper;

    public List<Course> selectAllCourse(){
        return courseMapper.selectAllCourse();
    }

    public Course selectByCourseNo(String CourseNo){
        return courseMapper.selectByCourseNo(CourseNo);
    }
}
