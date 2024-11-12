package com.example.bcp.entity;

import java.io.Serializable;
import java.util.Objects;

public class FavoriteId implements Serializable {
    private String studentNo;  // 学生编号
    private String favoriteNo; // 收藏编号
    private String favoriteInformationNo; // 新增的 FavoriteInformationNO 作为主键的一部分

    // Getter 和 Setter 方法
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

    public String getFavoriteInformationNo() {
        return favoriteInformationNo;
    }

    public void setFavoriteInformationNo(String favoriteInformationNo) {
        this.favoriteInformationNo = favoriteInformationNo;
    }

    // equals 方法，用于比较对象是否相等
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteId)) return false;
        FavoriteId that = (FavoriteId) o;
        return Objects.equals(studentNo, that.studentNo) &&
                Objects.equals(favoriteNo, that.favoriteNo) &&
                Objects.equals(favoriteInformationNo, that.favoriteInformationNo);
    }

    // hashCode 方法，用于生成哈希码
    @Override
    public int hashCode() {
        return Objects.hash(studentNo, favoriteNo, favoriteInformationNo);
    }
}
