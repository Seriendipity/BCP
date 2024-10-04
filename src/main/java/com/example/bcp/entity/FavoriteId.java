package com.example.bcp.entity;
import java.io.Serializable;
import java.util.Objects;

public class FavoriteId implements Serializable {
    private String studentNo;
    private String favoriteNo;

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getFavoriteNo() {
        return favoriteNo;
    }

    public void setFavoriteNo(String favoriteNo) {
        this.favoriteNo = favoriteNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteId)) return false;
        FavoriteId that = (FavoriteId) o;
        return Objects.equals(studentNo, that.studentNo) && Objects.equals(favoriteNo, that.favoriteNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNo, favoriteNo);
    }
}
