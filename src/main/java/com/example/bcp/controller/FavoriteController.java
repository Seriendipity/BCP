package com.example.bcp.controller;


import com.example.bcp.entity.Discussion;
import com.example.bcp.entity.Favorite;
import com.example.bcp.entity.Note;
import com.example.bcp.entity.Result;
import com.example.bcp.service.DiscussionService;
import com.example.bcp.service.FavoriteService;
import com.example.bcp.service.NoteService;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    DiscussionService discussionService;

    @Autowired
    NoteService noteService;

    @GetMapping("/selectByStudentNo")
    public Result selectByStudentNo(HttpServletRequest request) {
        String userId = request.getAttribute("username").toString();
        Map<String, Object> responseData = new HashMap<>();

        List<Favorite> selectByStudentNo = favoriteService.selectByStudentNo(userId);
        int index = 1;
        for (Favorite f : selectByStudentNo) {
            Map<String, Object> favorite = new HashMap<>();
            favorite.put("favoriteTitle", f.getFavoriteTitle());
            favorite.put("favoriteAuthority", f.getAuthority());
            favorite.put("fromStudentNo", f.getFromStudentNo());
            String favoriteInfoNo = f.getFavoriteInformationNO();
            if (favoriteInfoNo.startsWith("D")) {
                Discussion d = discussionService.selectByDiscussionId(favoriteInfoNo);
                favorite.put("discussionInfo", d.getDiscussionInformation());
                favorite.put("mentionedUser", d.getMentionedUser());
                favorite.put("discussionPt", d.getDiscussionPostingTime());
                favorite.put("imageUrl", d.getImageUrl());
                favorite.put("topic", d.getTopic());
            } else if (favoriteInfoNo.startsWith("N")) {
                Note n = noteService.selectByNoteNo(favoriteInfoNo);
                favorite.put("noteInfo", n.getNoteInformation());
            }
            responseData.put("favorite" + index, favorite);
            index++;
        }
        return Result.success(responseData);
    }

    @GetMapping("/selectByStarNoteFromOthers")
    public Result selectByStarOthers(HttpServletRequest request) {
        String studentNo = request.getAttribute("username").toString();
        List<Favorite> favoritesStarOthers = favoriteService.selectByStudentOthers(studentNo);
        Map<String, Object> responseData = new HashMap<>();
        int index = 1;
        for (Favorite f : favoritesStarOthers) {
            Map<String, Object> favorite = new HashMap<>();
            favorite.put("favoriteTitle", f.getFavoriteTitle());
            favorite.put("favoriteAuthority", f.getAuthority());
            favorite.put("fromStudentNo", f.getFromStudentNo());
            String favoriteInfoNo = f.getFavoriteInformationNO();
            if (favoriteInfoNo.startsWith("N")) {
                Note n = noteService.selectByNoteNo(favoriteInfoNo);
                favorite.put("noteInfo", n.getNoteInformation());
                favorite.put("favoriteInfoId", f.getFavoriteInformationNO());
                responseData.put("favorite" + index, favorite);
                index++;
            }
        }
        return Result.success(responseData);
    }

    @GetMapping("/selectByStarDiscussionFromOthers")
    public Result selectByStarDiscussionOthers(HttpServletRequest request) {
        String studentNo = request.getAttribute("username").toString();
        List<Favorite> favoritesStarOthers = favoriteService.selectByStudentOthers(studentNo);
        Map<String, Object> responseData = new HashMap<>();
        int index = 1;
        for (Favorite f : favoritesStarOthers) {
            Map<String, Object> favorite = new HashMap<>();
            favorite.put("favoriteTitle", f.getFavoriteTitle());
            favorite.put("favoriteAuthority", f.getAuthority());
            favorite.put("fromStudentNo", f.getFromStudentNo());
            String favoriteInfoNo = f.getFavoriteInformationNO();

            if (favoriteInfoNo.startsWith("D")) {
                Discussion d = discussionService.selectByDiscussionId(favoriteInfoNo);
                favorite.put("discussionInfo", d.getDiscussionInformation());
                favorite.put("mentionedUser", d.getMentionedUser());
                favorite.put("discussionPt", d.getDiscussionPostingTime());
                favorite.put("imageUrl", d.getImageUrl());
                favorite.put("topic", d.getTopic());
                responseData.put("favorite" + index, favorite);
                index++;
            }

        }
        return Result.success(responseData);
    }

    @GetMapping("/selectByStudentNoAndFavoriteTitle")
    public Result selectByStudentNoAndFavoriteTitle(@RequestParam String favoriteTitle,
                                                    HttpServletRequest request) {
        String studentNo = request.getAttribute("username").toString();
        List<Favorite> selectByStudentNoAndFavoriteTitle = favoriteService.selectByStudentNoAndFavoriteTitle(studentNo, favoriteTitle);
        int index = 1;
        Map<String, Object> responseData = new HashMap<>();
        for (Favorite f : selectByStudentNoAndFavoriteTitle) {
            Map<String, Object> favorite = new HashMap<>();
            favorite.put("favoriteTitle", f.getFavoriteTitle());
            favorite.put("favoriteAuthority", f.getAuthority());
            favorite.put("fromStudentNo", f.getFromStudentNo());
            String favoriteInfoNo = f.getFavoriteInformationNO();
            if (favoriteInfoNo.startsWith("D")) {
                Discussion d = discussionService.selectByDiscussionId(favoriteInfoNo);
                favorite.put("discussionInfo", d.getDiscussionInformation());
                favorite.put("mentionedUser", d.getMentionedUser());
                favorite.put("discussionPt", d.getDiscussionPostingTime());
                favorite.put("imageUrl", d.getImageUrl());
                favorite.put("topic", d.getTopic());
            } else if (favoriteInfoNo.startsWith("N")) {
                Note n = noteService.selectByNoteNo(favoriteInfoNo);
                favorite.put("noteInfo", n.getNoteInformation());
            }
            responseData.put("favorite" + index, favorite);
            index++;
        }
        return Result.success(responseData);
    }


    @GetMapping("/selectByStudentNoAndFavoriteNo")
    public Result selectByStudentNoAndFavoriteNo(@RequestParam String favoriteNo,
                                                 HttpServletRequest request) {
        String studentNo = request.getAttribute("username").toString();
        List<Favorite> selectByStudentNoAndFavoriteNo = favoriteService.selectByStudentNoAndFavoriteNo(studentNo, favoriteNo);
        int index = 1;
        Map<String, Object> responseData = new HashMap<>();
        for (Favorite f : selectByStudentNoAndFavoriteNo) {
            Map<String, Object> favorite = new HashMap<>();
            favorite.put("favoriteTitle", f.getFavoriteTitle());
            favorite.put("favoriteAuthority", f.getAuthority());
            favorite.put("fromStudentNo", f.getFromStudentNo());
            String favoriteInfoNo = f.getFavoriteInformationNO();
            if (favoriteInfoNo.startsWith("D")) {
                Discussion d = discussionService.selectByDiscussionId(favoriteInfoNo);
                favorite.put("discussionInfo", d.getDiscussionInformation());
                favorite.put("mentionedUser", d.getMentionedUser());
                favorite.put("discussionPt", d.getDiscussionPostingTime());
                favorite.put("imageUrl", d.getImageUrl());
                favorite.put("topic", d.getTopic());
            } else if (favoriteInfoNo.startsWith("N")) {
                Note n = noteService.selectByNoteNo(favoriteInfoNo);
                favorite.put("noteInfo", n.getNoteInformation());
            }
            responseData.put("favorite" + index, favorite);
            index++;
        }
        return Result.success(responseData);
    }


    @PostMapping("/insertFavoriteOwn")
    public Result insertFavoriteOwn(@RequestBody Map<String, String> requestData, HttpServletRequest request) {
        String studentNo = request.getAttribute("username").toString();
        String favoriteInformationNo = requestData.get("favoriteInformationNo");
        String favoriteTitle = requestData.get("favoriteTitle");
        Object Authority = requestData.get("authority");
        int authority;
        if (Authority == null) {
            authority = 0;
        } else {
            authority = Integer.parseInt(Authority.toString());
        }
        try {
            String favoriteNo = "F" + studentNo.substring(1);

            favoriteService.insertFavoriteOwn(favoriteNo, studentNo, favoriteInformationNo, favoriteTitle, authority);
            return Result.success("向收藏夹中添加内容成功");
        } catch (Exception e) {
            return Result.error("向收藏夹中添加内容失败");
        }
    }

    @PostMapping("/updateFavoriteAuthority")
    public Result updateFavoriteAuthority(@RequestBody Map<String, Object> requestData, HttpServletRequest request) {
        String studentNo = request.getAttribute("username").toString();
        String favoriteNo = requestData.get("favoriteNo").toString();
        String favoriteInfoNo = requestData.get("favoriteInfoNo").toString();
        try {
            favoriteService.updateFavoriteAuthority(studentNo, favoriteNo, favoriteInfoNo);
            return Result.success("修改收藏夹状态成功");
        } catch (Exception e) {
            return Result.error("修改收藏夹状态失败");
        }
    }

    @PostMapping("/insertFavoriteOther")
    public Result insertFavoriteOther(@RequestBody Map<String, String> requestData, HttpServletRequest request) {
        String studentNo = request.getAttribute("username").toString();
        String favoriteInformationNo = requestData.get("favoriteInformationNo");
        System.out.println(favoriteInformationNo);
        String FromStudentNo = noteService.selectByNoteNo(favoriteInformationNo).getStudentNo();
        System.out.println(FromStudentNo);
        String favoriteNo = "F" + FromStudentNo.substring(1);
        System.out.println(favoriteNo);
        String favoriteTitle = requestData.get("favoriteTitle");

        try {
            if (favoriteService.selectByStudentNoAndFavoriteNoAndFavoriteInformationNo(studentNo, favoriteNo, favoriteInformationNo) != null) {
                System.out.println("已收藏过");
                return Result.success("已收藏过该内容");
            } else {
                favoriteService.insertFavoriteOthers(favoriteNo, studentNo, favoriteInformationNo, favoriteTitle, FromStudentNo);
                System.out.println("收藏成功");
                return Result.success("收藏同学的收藏夹内容成功");
            }

        } catch (Exception e) {
            return Result.error("收藏同学的收藏夹内容失败");
        }

    }

    @PostMapping("/insertDiscussionFavoriteOther")
    public Result insertDiscussionFavoriteOther(@RequestBody Map<String, String> requestData, HttpServletRequest request) {
        String studentNo = request.getAttribute("username").toString();
        String favoriteInformationNo = requestData.get("favoriteInformationNo");
        System.out.println(favoriteInformationNo);
        String FromStudentNo = discussionService.selectByDiscussionId(favoriteInformationNo).getStudentNo();
        System.out.println(FromStudentNo);
        String favoriteNo = "F" + FromStudentNo.substring(1);
        System.out.println(favoriteNo);
        String favoriteTitle = requestData.get("favoriteTitle");

        try {
            if (favoriteService.selectByStudentNoAndFavoriteNoAndFavoriteInformationNo(studentNo, favoriteNo, favoriteInformationNo) != null) {
                System.out.println("已收藏过");
                return Result.success("已收藏过该内容");
            } else {
                favoriteService.insertFavoriteOthers(favoriteNo, studentNo, favoriteInformationNo, favoriteTitle, FromStudentNo);
                discussionService.increaseStarTimes(favoriteInformationNo);
                System.out.println("收藏成功");
                return Result.success("收藏同学的收藏夹内容成功");
            }

        } catch (Exception e) {
            return Result.error("收藏同学的收藏夹内容失败");
        }

    }
    @PostMapping("/deleteFavorite")
    public Result deleteFavorite(@RequestBody Map<String, String> requestData, HttpServletRequest request) {
        String favoriteNo = requestData.get("favoriteNo");
        String studentNo = request.getAttribute("username").toString();
        String favoriteInformationNo = request.getAttribute("favoriteInformationNo").toString();
        favoriteService.deleteFavorite(favoriteNo, studentNo, favoriteInformationNo);
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
