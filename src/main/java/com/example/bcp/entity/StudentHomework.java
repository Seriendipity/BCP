package com.example.bcp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@IdClass(StudentHomeworkId.class)
@Table(name = "StudentHomework")
public class StudentHomework {
    @Id
    @Column(name = "StudentNo",length = 10)
    private String StudentNo;

    @Id
    @Column(name = "HomeworkNo" , length = 10)
    private String HomeworkNo;

    @Column(name = "SubmitDescription", columnDefinition = "text")
    private String SubmitDescription;

    @Column(name = "SubmitPath" , length = 100)
    private String SubmitPath;

    @Column(name = "SubmitTime")
    private LocalDateTime SubmitTime;

    @Column(name = "SubmitGrade")
    private int SubmitGrade;

    @Column(name = "Comment",columnDefinition = "Text")
    private String Comment;

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getStudentNo() {
        return StudentNo;
    }

    public void setStudentNo(String studentNo) {
        StudentNo = studentNo;
    }

    public String getHomeworkNo() {
        return HomeworkNo;
    }

    public void setHomeworkNo(String homeworkNo) {
        HomeworkNo = homeworkNo;
    }

    public String getSubmitDescription() {
        return SubmitDescription;
    }

    public void setSubmitDescription(String submitDescription) {
        SubmitDescription = submitDescription;
    }

    public String getSubmitPath() {
        return SubmitPath;
    }

    public void setSubmitPath(String submitPath) {
        SubmitPath = submitPath;
    }

    public LocalDateTime getSubmitTime() {
        return SubmitTime;
    }

    public void setSubmitTime(LocalDateTime submitTime) {
        SubmitTime = submitTime;
    }

    public int getSubmitGrade() {
        return SubmitGrade;
    }

    public void setSubmitGrade(int submitGrade) {
        SubmitGrade = submitGrade;
    }
}
