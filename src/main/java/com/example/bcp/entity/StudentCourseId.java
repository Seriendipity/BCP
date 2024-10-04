package com.example.bcp.entity;

import java.io.Serializable;
import java.util.Objects;

public class StudentCourseId implements Serializable {

    private String studentNo;
    private String cid;

    public StudentCourseId() {}

    public StudentCourseId(String studentNo, String cid) {
        this.studentNo = studentNo;
        this.cid = cid;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentCourseId)) return false;
        StudentCourseId that = (StudentCourseId) o;
        return Objects.equals(studentNo, that.studentNo) && Objects.equals(cid, that.cid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNo, cid);
    }
}
