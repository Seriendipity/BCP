package com.example.bcp.service;

import com.example.bcp.entity.StudentCourse;
import com.example.bcp.mapper.StudentCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService {
    @Autowired
    private StudentCourseMapper studentCourseMapper;

    public List<StudentCourse> selectAllStudentCourse(){
        return studentCourseMapper.selectAllStudentCourse();
    }
    public List<String> selectAllStudentNo(String cid){
        return studentCourseMapper.selectAllStudentNo(cid);
    }


    public List<StudentCourse> selectByStudentNo(String StudentNo){
        return studentCourseMapper.selectByStudentNo(StudentNo);
    }

    public List<StudentCourse> selectByCid(String Cid){
        return studentCourseMapper.selectByCid(Cid);
    }
}
