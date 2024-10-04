package com.example.bcp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Course")
public class Course {
    @Id
    @Column(name = "CourseNo" , length = 8)
    private String CourseNo;

    @Column(name = "CourseName" , length = 20)
    private String CourseName;

    @Column(name = "CourseCredit")
    private short CourseCredit;

    public String getCourseNo() {
        return CourseNo;
    }

    public void setCourseNo(String courseNo) {
        CourseNo = courseNo;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public short getCourseCredit() {
        return CourseCredit;
    }

    public void setCourseCredit(short courseCredit) {
        CourseCredit = courseCredit;
    }
}
