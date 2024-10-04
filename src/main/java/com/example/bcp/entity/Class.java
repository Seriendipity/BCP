package com.example.bcp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Class")
public class Class {
    @Id
    @Column(name = "ClassNo" , length = 20)
    private String ClassNo;

    @Column(name = "Classname" , length = 20)
    private String ClassName;

    @Column(name = "ClassDepartment" , length = 20)
    private String ClassDepartment;

    @Column(name = "ClassMajor" , length = 20)
    private String ClassMajor;

    @Column(name = "StudentNumber")
    private int StudentNumber;

    public String getClassNo() {
        return ClassNo;
    }

    public void setClassNo(String classNo) {
        ClassNo = classNo;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getClassDepartment() {
        return ClassDepartment;
    }

    public void setClassDepartment(String classDepartment) {
        ClassDepartment = classDepartment;
    }

    public String getClassMajor() {
        return ClassMajor;
    }

    public void setClassMajor(String classMajor) {
        ClassMajor = classMajor;
    }

    public int getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        StudentNumber = studentNumber;
    }
}
