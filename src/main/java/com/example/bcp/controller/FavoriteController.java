package com.example.bcp.controller;


import com.example.bcp.entity.Favorite;
import com.example.bcp.entity.Result;
import com.example.bcp.service.FavoriteService;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    @PostMapping("/selectByStudentNo")
    public Result selectByStudentNo(@RequestBody Map<String, String> requestData) {
        String studentNo = requestData.get("studentNo");

        List<Favorite> selectByStudentNo = favoriteService.selectByStudentNo(studentNo);
        return Result.success(selectByStudentNo);
    }


    @PostMapping("/selectByStudentNoAndFavoriteTitle")
    public Result selectByStudentNoAndFavoriteTitle(@RequestBody Map<String, String> requestData) {
        String studentNo = requestData.get("studentNo");
        String favoriteTitle = requestData.get("favoriteTitle");

        List<Favorite> selectByStudentNoAndFavoriteTitle = favoriteService.selectByStudentNoAndFavoriteTitle(studentNo, favoriteTitle);
        return Result.success(selectByStudentNoAndFavoriteTitle);
    }


    @PostMapping("/selectByStudentNoAndFavoriteNo")
    public Result selectByStudentNoAndFavoriteNo(@RequestBody Map<String, String> requestData) {
        String studentNo = requestData.get("studentNo");
        String favoriteNo = requestData.get("favoriteNo");

        List<Favorite> selectByStudentNoAndFavoriteNo = favoriteService.selectByStudentNoAndFavoriteNo(studentNo, favoriteNo);
        return Result.success(selectByStudentNoAndFavoriteNo);
    }


    @PostMapping("/insertFavorite")
    public Result insertFavorite(@RequestBody Map<String, String> requestData) {
        String favoriteNo = requestData.get("favoriteNo");
        String studentNo = requestData.get("studentNo");
        String favoriteInformationNo = requestData.get("favoriteInformationNo");
        String favoriteTitle = requestData.get("favoriteTitle");

        favoriteService.insertFavorite(favoriteNo, studentNo, favoriteInformationNo, favoriteTitle);
        return Result.success();
    }


    @PostMapping("/deleteFavorite")
    public Result deleteFavorite(@RequestBody Map<String, String> requestData) {
        String favoriteNo = requestData.get("favoriteNo");
        String studentNo = requestData.get("studentNo");

        favoriteService.deleteFavorite(favoriteNo, studentNo);
        return Result.success();
    }


    @PostMapping("/updateFavoriteTitle")
    public Result updateFavoriteTitle(@RequestBody Map<String, String> requestData) {
        String favoriteTitle = requestData.get("favoriteTitle");
        String studentNo = requestData.get("studentNo");
        String favoriteNo = requestData.get("favoriteNo");

        favoriteService.updateFavoriteTitle(favoriteTitle, studentNo, favoriteNo);
        return Result.success();
    }


}
