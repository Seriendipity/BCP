package com.example.bcp.service;

import com.example.bcp.entity.Teacher;
import com.example.bcp.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    public List<Teacher> selectAllTeacher(){
        return teacherMapper.selectAllTeacher();
    }

    public Teacher selectByTeacherNo(String TeacherNo){
        return teacherMapper.selectByTeacherNo(TeacherNo);
    }

    public void  updateTeacherTitle(String TeacherTitle , String TeacherNo){
        teacherMapper.updateTeacherTitle(TeacherTitle,TeacherNo);
    }

    public void updateTeacherPassword(String Password , String TeacherNo){
        teacherMapper.updateTeacherPassword(Password,TeacherNo);
    }

    public void updateTeacherPicturePath(String PicturePath , String TeacherNo){
        teacherMapper.updateTeacherPicturePath(PicturePath,TeacherNo);
    }
}
