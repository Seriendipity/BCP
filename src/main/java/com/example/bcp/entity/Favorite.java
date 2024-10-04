package com.example.bcp.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@IdClass(FavoriteId.class)
@Table(name = "Favorite")
public class Favorite {
    @Id
    @Column(name = "StudentNo" , length = 10)
    private String StudentNo;

    @Id
    @Column(name = "FavoriteNo" , length = 10)
    private String FavoriteNo;

    @Column(name = "FavoriteInformationNO" , length = 10)
    private String  FavoriteInformationNO;

    @Column(name = "FavoriteTitle" , length = 10)
    private String FavoriteTitle;

    public String getStudentNo() {
        return StudentNo;
    }

    public void setStudentNo(String studentNo) {
        StudentNo = studentNo;
    }

    public String getFavoriteNo() {
        return FavoriteNo;
    }

    public void setFavoriteNo(String favoriteNo) {
        FavoriteNo = favoriteNo;
    }

    public String getFavoriteInformationNO() {
        return FavoriteInformationNO;
    }

    public void setFavoriteInformationNO(String favoriteInformationNO) {
        FavoriteInformationNO = favoriteInformationNO;
    }

    public String getFavoriteTitle() {
        return FavoriteTitle;
    }

    public void setFavoriteTitle(String favoriteTitle) {
        FavoriteTitle = favoriteTitle;
    }

}
