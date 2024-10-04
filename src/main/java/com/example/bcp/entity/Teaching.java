package com.example.bcp.entity;

import jakarta.persistence.*;

@Entity
@IdClass(TeachingId.class)
@Table(name = "Teaching")
public class Teaching {
    @Id
    @Column(name = "TeacherNo" , length = 8)
    private String TeacherNo;

    @Id
    @Column(name = "CourseNo" , length = 8)
    private String CourseNo;

    @Column(name = "Cid" , length = 10 , unique = true)
    private String Cid;

    @Column(name = "CourseIntroduction" , columnDefinition = "text")
    private String CourseIntroduction;

    @Column(name = "Syllabus" , columnDefinition = "text")
    private String Syllabus;

    @Column(name = "TeachingCalendar" , columnDefinition = "text")
    private String TeachingCalendar;

    @Column(name = "Language" , length = 10)
    private String Language;

    public String getTeacherNo() {
        return TeacherNo;
    }

    public void setTeacherNo(String teacherNo) {
        TeacherNo = teacherNo;
    }

    public String getCourseNo() {
        return CourseNo;
    }

    public void setCourseNo(String courseNo) {
        CourseNo = courseNo;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getCourseIntroduction() {
        return CourseIntroduction;
    }

    public void setCourseIntroduction(String courseIntroduction) {
        CourseIntroduction = courseIntroduction;
    }

    public String getSyllabus() {
        return Syllabus;
    }

    public void setSyllabus(String syllabus) {
        Syllabus = syllabus;
    }

    public String getTeachingCalendar() {
        return TeachingCalendar;
    }

    public void setTeachingCalendar(String teachingCalendar) {
        TeachingCalendar = teachingCalendar;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }
}
