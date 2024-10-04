package com.example.bcp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "Student")
public class Student {
    @Id
    @Column(name = "StudentNo" , length = 8)
    private String StudentNo;

    @Column(name = "StudentName" , length = 20)
    private String StudentName;

    @Column(name = "StudentSex" , length = 2)
    private String StudentSex;

    @Column(name = "StudentBirthday")
    private LocalDate StudentBirthday;

    @Column(name = "TotalCredit")
    private short TotalCredit;

    @Column(name = "ClassNo" , length = 10)
    private String ClassNo;

    @Column(name = "StudentEmail" , length = 20)
    private String StudentEmail;

    @Column(name = "password" , length = 20)
    private String password="password123";

    public String getStudentNo() {
        return StudentNo;
    }

    public void setStudentNo(String studentNo) {
        StudentNo = studentNo;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentSex() {
        return StudentSex;
    }

    public void setStudentSex(String studentSex) {
        StudentSex = studentSex;
    }

    public LocalDate getStudentBirthday() {
        return StudentBirthday;
    }

    public void setStudentBirthday(LocalDate studentBirthday) {
        StudentBirthday = studentBirthday;
    }

    public short getTotalCredit() {
        return TotalCredit;
    }

    public void setTotalCredit(short totalCredit) {
        TotalCredit = totalCredit;
    }

    public String getClassNo() {
        return ClassNo;
    }

    public void setClassNo(String classNo) {
        ClassNo = classNo;
    }

    public String getStudentEmail() {
        return StudentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        StudentEmail = studentEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
