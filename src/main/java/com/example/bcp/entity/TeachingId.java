package com.example.bcp.entity;

import java.io.Serializable;
import java.util.Objects;

public class TeachingId implements Serializable {
    private String teacherNo;
    private String courseNo;

    public TeachingId() {}

    public TeachingId(String teacherNo, String courseNo) {
        this.teacherNo = teacherNo;
        this.courseNo = courseNo;
    }

    public String getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeachingId)) return false;
        TeachingId that = (TeachingId) o;
        return Objects.equals(teacherNo, that.teacherNo) && Objects.equals(courseNo, that.courseNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherNo, courseNo);
    }
}
