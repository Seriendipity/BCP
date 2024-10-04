package com.example.bcp.entity;
import java.io.Serializable;
import java.util.Objects;

public class StudentHomeworkId implements Serializable {
    private String studentNo;
    private String homeworkNo;

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getHomeworkNo() {
        return homeworkNo;
    }

    public void setHomeworkNo(String homeworkNo) {
        this.homeworkNo = homeworkNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentHomeworkId)) return false;
        StudentHomeworkId that = (StudentHomeworkId) o;
        return Objects.equals(studentNo, that.studentNo) &&
                Objects.equals(homeworkNo, that.homeworkNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNo, homeworkNo);
    }
}
