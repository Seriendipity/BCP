package com.example.bcp.service;

import com.example.bcp.entity.Student;
import com.example.bcp.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    public List<Student> selectAllStudent(){
        return studentMapper.selectAllStudent();
    }

    public Student selectByStudentNo(String StudentNo){
        return studentMapper.selectByStudentNo(StudentNo);
    }

    public void updateStudentCredit(short TotalCredit , String StudentNo){
        studentMapper.updateStudentCredit(TotalCredit,StudentNo);
    }

    public void updateStudentPassword(String Password, String StudentNo){
        studentMapper.updateStudentPassword(Password,StudentNo);
    }

    public void updateStudentPicturePath(String PicturePath, String StudentNo){
        studentMapper.updateStudentPicturePath(PicturePath,StudentNo);
    }

}
