package com.example.bcp.service;

import com.example.bcp.entity.StudentHomework;
import com.example.bcp.mapper.StudentHomeworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentHomeworkService {
    @Autowired
    private StudentHomeworkMapper studentHomeworkMapper;

    public List<StudentHomework> selectAllStudentHomework(){
        return studentHomeworkMapper.selectAllStudentHomework();
    }

    public List<StudentHomework> selectByStudentNo(String StudentNo){
        return studentHomeworkMapper.selectByStudentNo(StudentNo);
    }

    public List<StudentHomework> selectByHomeworkNo(String HomeworkNo){
        return studentHomeworkMapper.selectByHomeworkNo(HomeworkNo);
    }

    public List<StudentHomework> selectCorrectingHomework(String HomeworkNo){
        return studentHomeworkMapper.selectCorrectingHomework(HomeworkNo);
    }

    public StudentHomework selectByStudentNoAndHomeworkNo(String StudentNo, String HomeworkNo){
        return studentHomeworkMapper.selectByStudentNoAndHomeworkNo(StudentNo,HomeworkNo);
    }

    public void insertStudentHomework(String StudentNo, String HomeworkNo, String SubmitDescription,
                                      String SubmitPath , LocalDateTime SubmitTime,String Comment){
        studentHomeworkMapper.insertStudentHomework(StudentNo,HomeworkNo,SubmitDescription,
                                                SubmitPath,SubmitTime,Comment);
    }

    public void updateStudentHomeworkPath(String SubmitPath , String StudentNo , String HomeworkNo){
        studentHomeworkMapper.updateStudentHomeworkPath(SubmitPath,StudentNo,HomeworkNo);
    }

    public void updateStudentHomeworkSubmitGrade(int SubmitGrade,String StudentNo,String HomeworkNo,String Comment){
        studentHomeworkMapper.updateStudentHomeworkSubmitGrade(SubmitGrade,StudentNo,HomeworkNo ,Comment);
    }

    public List<StudentHomework> selectByIsTeacherAndHomeworkNo(String HomeworkNo){
        return studentHomeworkMapper.selectByIsTeacherAndHomeworkNo(HomeworkNo);
    }


    public void updateIsTeacherGrade(boolean isTeacherGrade,String StudentNo,String HomeworkNo){
        studentHomeworkMapper.updateStudentHomeworkIsTeacherGrade(isTeacherGrade,StudentNo,HomeworkNo);
    }

    public void updateVisible(String HomeworkNo){
        studentHomeworkMapper.updateVisible(HomeworkNo);
    }
}
