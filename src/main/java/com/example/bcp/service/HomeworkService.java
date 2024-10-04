package com.example.bcp.service;

import com.example.bcp.entity.Homework;
import com.example.bcp.mapper.HomeworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HomeworkService {
    @Autowired
    private HomeworkMapper homeworkMapper;

    public List<Homework> selectAllHomework(){
        return homeworkMapper.selectAllHomework();
    }

    public List<Homework> selectByCid(String Cid){
        return homeworkMapper.selectByCid(Cid);
    }

    public void insertHomework(String HomeworkNo, String Cid, String HomeworkDescription,
                               LocalDateTime StartTime , LocalDateTime EndTime , int HomeworkGrade){
        homeworkMapper.insertHomework(HomeworkNo,Cid,HomeworkDescription,
                                StartTime,EndTime,HomeworkGrade);
    }

    public void deleteHomework(String HomeworkNo){
        homeworkMapper.deleteHomework(HomeworkNo);
    }

    public void updateHomeworkEndTime(LocalDateTime EndTime , String HomeworkNo){
        homeworkMapper.updateHomeworkEndTime(EndTime,HomeworkNo);
    }

}
