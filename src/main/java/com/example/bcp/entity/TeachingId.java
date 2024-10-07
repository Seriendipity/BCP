package com.example.bcp.entity;

import java.io.Serializable;
import java.util.Objects;

public class TeachingId implements Serializable {
    private String teacherNo;
    private String courseNo;
    private String semester; // 注意：这里使用小写字母

    public TeachingId() {}

    public TeachingId(String teacherNo, String courseNo, String semester) {
        this.teacherNo = teacherNo;
        this.courseNo = courseNo;
        this.semester = semester;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeachingId)) return false;
        TeachingId that = (TeachingId) o;
        return Objects.equals(teacherNo, that.teacherNo) &&
                Objects.equals(courseNo, that.courseNo) &&
                Objects.equals(semester, that.semester); // 包含 semester
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherNo, courseNo, semester); // 包含 semester
    }
}
