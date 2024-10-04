package com.example.bcp.service;

import com.example.bcp.entity.Class;
import com.example.bcp.mapper.ClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {
    @Autowired
    private ClassMapper classMapper;

    public List<Class> selectAllClass(){
        return classMapper.selectAllClass();
    }

    public Class selectByClassNo(String ClassNo){
        return classMapper.selectByClassNo(ClassNo);
    }

    public void updateStudentNumber(String ClassNo , int StudentNumber){
        classMapper.updateStudentNumber(ClassNo,StudentNumber);
    }

}
