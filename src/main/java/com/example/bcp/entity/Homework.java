package com.example.bcp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "Homework")
public class Homework {
    @Id
    @Column(name = "HomeworkNo" , length = 10)
    private String HomeworkNo;

    @Column(name = "Cid" , length = 10)
    private String Cid;

    @Column(name = "HomeworkDescription" , columnDefinition = "text")
    private String HomeworkDescription;

    @Column(name = "StartTime")
    private LocalDateTime StartTime;

    @Column(name = "EndTime")
    private LocalDateTime EndTime;

    @Column(name = "HomeworkGrade")
    private int HomeworkGrade;

    @Column(name = "isVisible")
    private Boolean isVisible;

    @Column(name = "HomeworkPath",length = 100)
    private String HomeworkPath;

    @Column(name = "homeworkInfo" , columnDefinition = "text")
    private String homeworkInfo;

    @Column(name = "isPeerView")
    private Boolean isPeerView;

    public String getHomeworkInfo() {
        return homeworkInfo;
    }

    public void setHomeworkInfo(String homeworkInfo) {
        this.homeworkInfo = homeworkInfo;
    }

    public String getHomeworkPath() {
        return HomeworkPath;
    }

    public void setHomeworkPath(String homeworkPath) {
        HomeworkPath = homeworkPath;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public String getHomeworkNo() {
        return HomeworkNo;
    }

    public void setHomeworkNo(String homeworkNo) {
        HomeworkNo = homeworkNo;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getHomeworkDescription() {
        return HomeworkDescription;
    }

    public void setHomeworkDescription(String homeworkDescription) {
        HomeworkDescription = homeworkDescription;
    }

    public LocalDateTime getStartTime() {
        return StartTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        StartTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return EndTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        EndTime = endTime;
    }

    public int getHomeworkGrade() {
        return HomeworkGrade;
    }

    public void setHomeworkGrade(int homeworkGrade) {
        HomeworkGrade = homeworkGrade;
    }
}
