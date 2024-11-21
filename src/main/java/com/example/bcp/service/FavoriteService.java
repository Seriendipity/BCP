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

    public List<Favorite> selectByStudentOwn(String StudentNo){
        return favoriteMapper.selectByStudentOwn(StudentNo);
    }

    public List<Favorite> selectByStudentOthers(String StudentNo){
        return favoriteMapper.selectByStudentOthers(StudentNo);
    }
    public List<Favorite> selectByFavoriteTitle(String StudentNo,String FavoriteTitle){
        return favoriteMapper.selectByFavoriteTitle(StudentNo,FavoriteTitle);
    }

    public Favorite selectByStudentNoAndFavoriteNoAndFavoriteInformationNo(String StudentNo,String FavoriteNo,String FavoriteInformationNo){
        return favoriteMapper.selectByStudentNoAndFavoriteNoAndFavoriteInformationNo(StudentNo,FavoriteNo,FavoriteInformationNo);
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

    public void insertFavoriteOwn(String FavoriteNo,String StudentNo,
                               String FavoriteInformationNo,String FavoriteTitle,
                                  int Authority,String FromStudentNo){
        favoriteMapper.insertFavoriteOwn(FavoriteNo,StudentNo,
                FavoriteInformationNo,FavoriteTitle,Authority,FromStudentNo);
    }
    public void insertFavoriteOthers(String FavoriteNo,String StudentNo,
                                     String FavoriteInformationNo,String FavoriteTitle,String FromStudentNo){
        favoriteMapper.insertFavoriteOthers(FavoriteNo,StudentNo,FavoriteInformationNo,
                FavoriteTitle,FromStudentNo);
    }
    public void deleteFavorite(String FavoriteNo, String StudentNo,String FavoriteInformationNO){
        favoriteMapper.deleteFavorite(FavoriteNo,StudentNo,FavoriteInformationNO);
    }

    public void updateFavoriteTitle(String FavoriteTitle,String StudentNo , String FavoriteNo){
        favoriteMapper.updateFavoriteTitle(FavoriteTitle,StudentNo,FavoriteNo);
    }

    public void updateFavoriteAuthority(String StudentNo,String FavoriteNo,String FavoriteInformationNO){
        favoriteMapper.updateFavoriteAuthority(StudentNo,FavoriteNo,FavoriteInformationNO);
    }

    public String MaxFavorite(){
        return favoriteMapper.MaxFavorite();
    }
}
