package com.example.bcp.entity;

import jakarta.persistence.*;

@Entity
@IdClass(StudentCourseId.class)
@Table(name = "StudentCourse")
public class StudentCourse {
    @Id
    @Column(name = "StudentNo" , length = 8)
    private String StudentNo;

    @Id
    @Column(name = "Cid" , length = 10)
    private String Cid;

    @Column(name = "Grade")
    private int Grade;

    public String getStudentNo() {
        return StudentNo;
    }

    public void setStudentNo(String studentNo) {
        StudentNo = studentNo;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public int getGrade() {
        return Grade;
    }

    public void setGrade(int grade) {
        Grade = grade;
    }
}
