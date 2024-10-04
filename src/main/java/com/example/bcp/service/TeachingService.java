package com.example.bcp.service;

import com.example.bcp.entity.Teaching;
import com.example.bcp.mapper.TeachingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeachingService {
    @Autowired
    private TeachingMapper teachingMapper;

    public List<Teaching> selectAllTeaching(){
        return teachingMapper.selectAllTeaching();
    }

    public List<Teaching> selectByTeacherNo(String TeacherNo){
        return teachingMapper.selectByTeacherNo(TeacherNo);
    }

    public List<Teaching> selectByCourseNo(String CourseNo){
        return teachingMapper.selectByCourseNo(CourseNo);
    }

    public Teaching selectByCid(String Cid){
        return teachingMapper.selectByCid(Cid);
    }

    public List<Teaching> selectByTeacherNoAndCourseNo(String TeacherNo , String CourseNo){
        return teachingMapper.selectByTeacherNoAndCourseNo(TeacherNo,CourseNo);
    }
}
