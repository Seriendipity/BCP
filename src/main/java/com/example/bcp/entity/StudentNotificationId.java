package com.example.bcp.entity;
import java.io.Serializable;
import java.util.Objects;

public class StudentNotificationId implements Serializable {
    private String studentNo;
    private String notificationNo;

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getNotificationNo() {
        return notificationNo;
    }

    public void setNotificationNo(String notificationNo) {
        this.notificationNo = notificationNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentNotificationId)) return false;
        StudentNotificationId that = (StudentNotificationId) o;
        return Objects.equals(studentNo, that.studentNo) &&
                Objects.equals(notificationNo, that.notificationNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNo, notificationNo);
    }
}
