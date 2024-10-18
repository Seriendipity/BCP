package com.example.bcp.controller;


import com.example.bcp.entity.Favorite;
import com.example.bcp.entity.Result;
import com.example.bcp.service.FavoriteService;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    @GetMapping("/selectByStudentNo")
    public Result selectByStudentNo(@RequestParam("studentNO") String studentNo) {
        List<Favorite> selectByStudentNo = favoriteService.selectByStudentNo(studentNo);
        return Result.success(selectByStudentNo);
    }

    @GetMapping("/selectByStudentNoAndFavoriteTitle")
    public Result selectByStudentNoAndFavoriteTitle(@RequestParam("studentNo") String studentNo,
                                                    @RequestParam("favoriteTitle") String favoriteTitle) {
        List<Favorite> selectByStudentNoAndFavoriteTitle = favoriteService.selectByStudentNoAndFavoriteTitle(studentNo, favoriteTitle);
        return Result.success(selectByStudentNoAndFavoriteTitle);
    }

    @GetMapping("/selectByStudentNoAndFavoriteNo")
    public Result selectByStudentNoAndFavoriteNo(@RequestParam("studentNo") String studentNo,
                                                 @RequestParam("favoriteNo") String favoriteNO) {
        List<Favorite> selectByStudentNoAndFavoriteNo = favoriteService.selectByStudentNoAndFavoriteNo(studentNo, favoriteNO);
        return Result.success(selectByStudentNoAndFavoriteNo);
    }

    @PostMapping("/insertFavorite")
    public Result insertFavorite(@RequestParam("favoriteNo") String favoriteNo,
                                 @RequestParam("studentNo") String studentNo,
                                 @RequestParam("favoriteInformationNo") String favoriteInformationNo,
                                 @RequestParam("favoriteTitle") String favoriteTitle) {

        favoriteService.insertFavorite(favoriteNo, studentNo, favoriteInformationNo, favoriteTitle);
        return Result.success();

    }

    @PostMapping("/deleteFavorite")
    public Result deleteFavorite(@RequestParam("favoriteNo") String favoriteNo,
                                 @RequestParam("studentNo") String studentNo) {
        favoriteService.deleteFavorite(favoriteNo, studentNo);
        return Result.success();
    }

    @PostMapping("/updateFavoriteTitle")
    public Result updateFavoriteTitle(@RequestParam("favoriteTitle") String favoriteTitle,
                                      @RequestParam("studentNo") String studentNo,
                                      @RequestParam("favoriteNo") String favoriteNo) {
        favoriteService.updateFavoriteTitle(favoriteTitle, studentNo, favoriteNo);
        return Result.success();
    }

}
