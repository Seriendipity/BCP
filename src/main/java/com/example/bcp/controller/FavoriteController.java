package com.example.bcp.controller;


import com.example.bcp.entity.Favorite;
import com.example.bcp.entity.Result;
import com.example.bcp.service.FavoriteService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    @GetMapping("/selectByStudentNo")
    public Result selectByStudentNo(HttpServletRequest request) {
        String userId = request.getAttribute("username").toString();
        Map<String,Object> responseData = new HashMap<>();

        List<Favorite> selectByStudentNo = favoriteService.selectByStudentNo(userId);
        responseData.put("userId",userId);
        responseData.put("Info",selectByStudentNo);
        return Result.success(responseData);
    }


    @GetMapping("/selectByStudentNoAndFavoriteTitle")
    public Result selectByStudentNoAndFavoriteTitle(@RequestParam String studentNo,
                                                    @RequestParam String favoriteTitle) {
        List<Favorite> selectByStudentNoAndFavoriteTitle = favoriteService.selectByStudentNoAndFavoriteTitle(studentNo, favoriteTitle);
        return Result.success(selectByStudentNoAndFavoriteTitle);
    }


    @GetMapping("/selectByStudentNoAndFavoriteNo")
    public Result selectByStudentNoAndFavoriteNo(@RequestParam String studentNo,
                                                 @RequestParam String favoriteNo) {

        List<Favorite> selectByStudentNoAndFavoriteNo = favoriteService.selectByStudentNoAndFavoriteNo(studentNo, favoriteNo);
        return Result.success(selectByStudentNoAndFavoriteNo);
    }


    @GetMapping("/insertFavorite")
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
