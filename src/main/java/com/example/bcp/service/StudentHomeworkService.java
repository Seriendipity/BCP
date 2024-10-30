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

    public StudentHomework selectByStudentNoAndHomeworkNo(String StudentNo, String HomeworkNo){
        return studentHomeworkMapper.selectByStudentNoAndHomeworkNo(StudentNo,HomeworkNo);
    }

    public void insertStudentHomework(String StudentNo, String HomeworkNo, String SubmitDescription,
                                      String SubmitPath , LocalDateTime SubmitTime, int SubmitGrade){
        studentHomeworkMapper.insertStudentHomework(StudentNo,HomeworkNo,SubmitDescription,
                                                SubmitPath,SubmitTime,SubmitGrade);
    }

    public void updateStudentHomeworkPath(String SubmitPath , String StudentNo , String HomeworkNo){
        studentHomeworkMapper.updateStudentHomeworkPath(SubmitPath,StudentNo,HomeworkNo);
    }

    public void updateStudentHomeworkSubmitGrade(int SubmitGrade,String StudentNo,String HomeworkNo){
        studentHomeworkMapper.updateStudentHomeworkSubmitGrade(SubmitGrade,StudentNo,HomeworkNo);
    }
}
