package com.example.bcp.service;

import com.example.bcp.entity.Favorite;
import com.example.bcp.mapper.FavoriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteMapper favoriteMapper;

    public List<Favorite> selectAllFavorite(){
        return favoriteMapper.selectAllFavorite();
    }

    public List<Favorite> selectByStudentNo(String StudentNo){
        return favoriteMapper.selectByStudentNo(StudentNo);
    }

    public List<Favorite> selectByStudentNoAndFavoriteTitle(String StudentNo , String FavoriteTitle){
        return favoriteMapper.selectByStudentNoAndFavoriteTitle(StudentNo,FavoriteTitle);
    }

    public List<Favorite> selectByStudentNoAndFavoriteNo(String StudentNo, String FavoriteNo){
        return favoriteMapper.selectByStudentNoAndFavoriteNo(StudentNo,FavoriteNo);
    }

    public void insertFavorite(String FavoriteNo,String StudentNo,
                               String FavoriteInformationNo,String FavoriteTitle){
        favoriteMapper.insertFavorite(FavoriteNo,StudentNo,
                FavoriteInformationNo,FavoriteTitle);
    }

    public void deleteFavorite(String FavoriteNo, String StudentNo){
        favoriteMapper.deleteFavorite(FavoriteNo,StudentNo);
    }

    public void updateFavoriteTitle(String FavoriteTitle,String StudentNo , String FavoriteNo){
        favoriteMapper.updateFavoriteTitle(FavoriteTitle,StudentNo,FavoriteNo);
    }

    public void updateFavoriteAuthority(String StudentNo,String FavoriteNo,String FavoriteInformationNO){
        favoriteMapper.updateFavoriteAuthority(StudentNo,FavoriteNo,FavoriteInformationNO);
    }
}
