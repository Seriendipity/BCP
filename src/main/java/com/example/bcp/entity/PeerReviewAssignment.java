package com.example.bcp.entity;

import java.util.Date;

public class PeerReviewAssignment {
    private String revieweeNo;   // 被评审者学号
    private String reviewerNo;   // 评审者学号
    private String homeworkNo;   // 作业编号
    private boolean reviewStatus; // 评审状态
    private String comment;      // 评审评论
    private Date startTime;      // 评审开始时间
    private Date endTime;        // 评审结束时间

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
