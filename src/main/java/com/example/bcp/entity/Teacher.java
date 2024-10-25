package com.example.bcp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "Teacher")
public class Teacher {
    @Id
    @Column(name = "TeacherNo", length = 8)
    private String TeacherNo;

    @Column(name = "TeacherName" , length = 20)
    private String TeacherName;

    @Column(name = "TeacherSex" , length = 2)
    private String TeacherSex;

    @Column(name = "TeacherBirthday")
    private LocalDate TeacherBirthday;

    @Column(name = "TeacherTitle" , length = 10)
    private String TeacherTitle;

    @Column(name = "TeacherEmail" , length = 20)
    private String TeacherEmail;

    @Column(name = "Password" , length = 20)
    private String Password= "123456";

    @Column(name = "PicturePath" ,length = 100)
    private String PicturePath;

    @Column(name = "TeacherInfo",columnDefinition = "text")
    private String TeacherInfo;

    @Column(name = "TeacherStation" , length = 30)
    private String TeacherStation;

    @Column(name = "TeacherPhone",length = 20)
    private String TeacherPhone;

    public String getTeacherInfo() {
        return TeacherInfo;
    }

    public void setTeacherInfo(String teacherInfo) {
        TeacherInfo = teacherInfo;
    }

    public String getTeacherStation() {
        return TeacherStation;
    }

    public void setTeacherStation(String teacherStation) {
        TeacherStation = teacherStation;
    }

    public String getTeacherPhone() {
        return TeacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        TeacherPhone = teacherPhone;
    }

    public String getTeacherNo() {
        return TeacherNo;
    }

    public void setTeacherNo(String teacherNo) {
        TeacherNo = teacherNo;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getTeacherSex() {
        return TeacherSex;
    }

    public void setTeacherSex(String teacherSex) {
        TeacherSex = teacherSex;
    }

    public LocalDate getTeacherBirthday() {
        return TeacherBirthday;
    }

    public void setTeacherBirthday(LocalDate teacherBirthday) {
        TeacherBirthday = teacherBirthday;
    }

    public String getTeacherTitle() {
        return TeacherTitle;
    }

    public void setTeacherTitle(String teacherTitle) {
        TeacherTitle = teacherTitle;
    }

    public String getTeacherEmail() {
        return TeacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        TeacherEmail = teacherEmail;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPicturePath() {
        return PicturePath;
    }

    public void setPicturePath(String picturePath) {
        PicturePath = picturePath;
    }
}
