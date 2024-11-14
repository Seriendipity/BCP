package com.example.bcp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "PeerReviewAssignment")
public class PeerReviewAssignment {
    @Id
    @Column(name = "RevieweeNo" , length = 10)
    private String revieweeNo;   // 被评审者学号

    @Id
    @Column(name = "ReviewerNo" , length = 10)
    private String reviewerNo;   // 评审者学号

    @Id
    @Column(name = "HomeworkNo" , length = 10)
    private String homeworkNo;   // 作业编号

    @Column(name = "ReviewStatus")
    private boolean reviewStatus; // 评审状态

    @Column(name = "Grade")
    private int grade;           // 分数

    @Column(name = "Comment" , columnDefinition = "text")
    private String comment;      // 评审评论

    @Column(name = "StartTime")
    private LocalDateTime startTime;      // 评审开始时间

    @Column(name = "EndTime")
    private LocalDateTime endTime;        // 评审结束时间

    // Getters and Setters
    public String getRevieweeNo() {
        return revieweeNo;
    }

    public void setRevieweeNo(String revieweeNo) {
        this.revieweeNo = revieweeNo;
    }

    public String getReviewerNo() {
        return reviewerNo;
    }

    public void setReviewerNo(String reviewerNo) {
        this.reviewerNo = reviewerNo;
    }

    public String getHomeworkNo() {
        return homeworkNo;
    }

    public void setHomeworkNo(String homeworkNo) {
        this.homeworkNo = homeworkNo;
    }

    public boolean getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(boolean reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
