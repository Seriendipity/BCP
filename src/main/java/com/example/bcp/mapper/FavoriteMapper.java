package com.example.bcp.mapper;

import com.example.bcp.entity.Favorite;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FavoriteMapper {
    @Select("Select * from Favorite")
    List<Favorite> selectAllFavorite();

    @Select("Select * from Favorite Where StudentNo = #{StudentNo}")
    List<Favorite> selectByStudentNo(String StudentNo);

    @Select("Select * from Favorite Where StudentNo = #{StudentNo} and FavoriteTitle = #{FavoriteTitle}")
    List<Favorite> selectByStudentNoAndFavoriteTitle(String StudentNo , String FavoriteTitle);

    @Select("Select * from Favorite Where StudentNo = #{StudentNo} and FavoriteNo = #{FavoriteNo}")
    List<Favorite> selectByStudentNoAndFavoriteNo(String StudentNo, String FavoriteNo);

    @Insert("Insert into Favorite(FavoriteNo,StudentNo,FavoriteInformationNo,FavoriteTitle,Authority)" +
            " values(#{FavoriteNo},#{StudentNo},#{FavoriteInformationNo},#{FavoriteTitle},0)")
    void insertFavorite(String FavoriteNo,String StudentNo,
                        String FavoriteInformationNo,String FavoriteTitle);

    @Delete("Delete from Favorite Where FavoriteNo = #{FavoriteNo} and StudentNo = #{StudentNo}")
    void deleteFavorite(String FavoriteNo, String StudentNo);

    @Update("Update Favorite Set FavoriteTitle = #{FavoriteTitle} Where StudentNo=#{StudentNo} and FavoriteNo =#{FavoriteNo}")
    void updateFavoriteTitle(String FavoriteTitle,String StudentNo , String FavoriteNo);

    @Update("Update Favorite Set Authority = 1-Authority Where FavoriteNo=#{FavoriteNo} and FavoriteInformationNO = #{FavoriteInformationNO} and StudentNo = #{StudentNo}")
    void updateFavoriteAuthority(String StudentNo,String FavoriteNo,String FavoriteInformationNO);
}
