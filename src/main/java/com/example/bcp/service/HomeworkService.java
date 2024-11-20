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

    public List<Homework> selectAllHomework() {
        return homeworkMapper.selectAllHomework();
    }

    public Homework selectByHomeworkNo(String HomeworkNo) {
        return homeworkMapper.selectByHomeworkNo(HomeworkNo);
    }

    public List<Homework> selectByCid(String Cid) {
        return homeworkMapper.selectByCid(Cid);
    }

    public List<Homework> selectByCidAndVisible(String Cid) {
        return homeworkMapper.selectByCidAndVisible(Cid);
    }

    public void insertHomework(String Cid, String HomeworkNo, String HomeworkDescription,
                               LocalDateTime StartTime, LocalDateTime EndTime, int HomeworkGrade,
                               String homeworkInfo, String HomeworkPath) {
        homeworkMapper.insertHomework(Cid, HomeworkNo, HomeworkDescription,
                StartTime, EndTime, HomeworkGrade, homeworkInfo, HomeworkPath);
    }

    public void deleteHomework(String HomeworkNo) {
        homeworkMapper.deleteHomework(HomeworkNo);
    }

    public void updateHomeworkEndTime(LocalDateTime EndTime, String HomeworkNo) {
        homeworkMapper.updateHomeworkEndTime(EndTime, HomeworkNo);
    }

    public void updateHomeworkPath(String HomeworkPath, String HomeworkNo) {
        homeworkMapper.updateHomeworkPath(HomeworkPath, HomeworkNo);
    }

    public void updateHomeworkDescription(String HomeworkDescription, String HomeworkNo) {
        homeworkMapper.updateHomeworkDescription(HomeworkDescription, HomeworkNo);
    }

    public void updateHomeworkGrade(int HomeworkGrade, String HomeworkNo) {
        homeworkMapper.updateHomeworkGrade(HomeworkGrade, HomeworkNo);
    }

    public void updateHomeworkStartTime(LocalDateTime StartTime, String HomeworkNo) {
        homeworkMapper.updateHomeworkStartTime(StartTime, HomeworkNo);
    }

    public void updateHomeworkInfo(String homeworkInfo, String HomeworkNo) {
        homeworkMapper.updateHomeworkInfo(homeworkInfo, HomeworkNo);
    }

    public void updateIsPeerReview(boolean isPeerReview, String HomeworkNo) {
        homeworkMapper.updateIsPeerReview(isPeerReview, HomeworkNo);
    }

    public void updateHomeworkIsVisible(String HomeworkNo){
        homeworkMapper.updateHomeworkIsVisible(HomeworkNo);
    }
}