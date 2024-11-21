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

    @Select("Select * from Favorite Where StudentNo = #{StudentNo} and FavoriteNo = #{FavoriteNo} and FavoriteInformationNO = #{FavoriteInformationNo}")
    Favorite selectByStudentNoAndFavoriteNoAndFavoriteInformationNo(String StudentNo,String FavoriteNo,String FavoriteInformationNo);

    @Select("Select * from Favorite Where StudentNo = #{StudentNo} and IsOwn = 1")
    List<Favorite> selectByStudentOwn(String StudentNo);

    @Select("Select * from Favorite Where StudentNo = #{StudentNo} and IsOwn = 0")
    List<Favorite> selectByStudentOthers(String StudentNo);

    @Select("Select * from Favorite Where StudentNo = #{StudentNo} and FavoriteTitle = #{FavoriteTitle}")
    List<Favorite> selectByStudentNoAndFavoriteTitle(String StudentNo , String FavoriteTitle);

    @Select("Select * from Favorite Where StudentNo = #{StudentNo} and FavoriteNo = #{FavoriteNo}")
    List<Favorite> selectByStudentNoAndFavoriteNo(String StudentNo, String FavoriteNo);

    @Select("Select * from Favorite Where StudentNo = #{StudentNo} and FavoriteTitle like concat('%', #{FavoriteTitle}, '%')")
    List<Favorite> selectByFavoriteTitle(String StudentNo,String FavoriteTitle);

    @Insert("Insert into Favorite(FavoriteNo,StudentNo,FavoriteInformationNo,FavoriteTitle,Authority,IsOwn,FromStudentNo)" +
            " values(#{FavoriteNo},#{StudentNo},#{FavoriteInformationNo},#{FavoriteTitle},#{Authority},1,#{FromStudentNo})")
    void insertFavoriteOwn(String FavoriteNo,String StudentNo,
                        String FavoriteInformationNo,String FavoriteTitle,int Authority,
                           String FromStudentNo);

    @Insert("Insert into Favorite(FavoriteNo,StudentNo,FavoriteInformationNo,FavoriteTitle,Authority,IsOwn,FromStudentNo)" +
            " values(#{FavoriteNo},#{StudentNo},#{FavoriteInformationNo},#{FavoriteTitle},0,0,#{FromStudentNo})")
    void insertFavoriteOthers(String FavoriteNo,String StudentNo,
                           String FavoriteInformationNo,String FavoriteTitle,String FromStudentNo);

    @Delete("Delete from Favorite Where FavoriteNo = #{FavoriteNo} and StudentNo = #{StudentNo} and FavoriteInformationNO = #{FavoriteInformationNO}")
    void deleteFavorite(String FavoriteNo, String StudentNo,String FavoriteInformationNO);

    @Update("Update Favorite Set FavoriteTitle = #{FavoriteTitle} Where StudentNo=#{StudentNo} and FavoriteNo =#{FavoriteNo}")
    void updateFavoriteTitle(String FavoriteTitle,String StudentNo , String FavoriteNo);

    @Update("Update Favorite Set Authority = 1-Authority Where FavoriteNo=#{FavoriteNo} and FavoriteInformationNO = #{FavoriteInformationNO} and StudentNo = #{StudentNo}")
    void updateFavoriteAuthority(String StudentNo,String FavoriteNo,String FavoriteInformationNO);
    @Select("Select MAX(FavoriteNo) from Favorite")
    String MaxFavorite();
}
