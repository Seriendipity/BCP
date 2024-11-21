package com.example.bcp.controller;


import com.example.bcp.entity.Comment;
import com.example.bcp.entity.Result;
import com.example.bcp.service.CommentService;
import com.example.bcp.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private StudentService studentService;

    /**
     * 查询某个帖子的所有评论
     * */
    @GetMapping(value = "/all")
    public Result getAllComments(@RequestParam String discussionId, HttpServletRequest request) {
        List<Comment> comments = commentService.selectByDiscussionId(discussionId);

        // 根据 PostingTime 进行排序，最近的时间在最前面
        comments.sort((c1, c2) -> c2.getCommentPostingTime().compareTo(c1.getCommentPostingTime()));

        String username = request.getAttribute("username").toString();
        Map<String, Object> responseData = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int index = 1;

        for (Comment c : comments) {
            Map<String, Object> commentInfo = new HashMap<>();
            commentInfo.put("Index", index);
            commentInfo.put("CommentId", c.getCommentId());
            commentInfo.put("Information", c.getCommentInformation());
            commentInfo.put("PostingTime", c.getCommentPostingTime().format(formatter));
            commentInfo.put("Likes", c.getLikesNumber());
            commentInfo.put("DiscussionId", c.getDiscussionId());
            commentInfo.put("commentUsername", studentService.selectByStudentNo(c.getPostStudent()).getStudentName());

            // 动态生成 key，以从最近到最远排序
            responseData.put("comment" + index, commentInfo);
            index++;
        }

        return Result.success(responseData);
    }


    /**
     *  给某条帖子插入评论
     */
    @PostMapping(value = "/insert" , consumes = "multipart/form-data")
    public Result insertComment(@RequestParam String discussionId,
                                @RequestParam String commentInfo,
                                @RequestParam String imgUrl,
                                @RequestParam String mentionedUser,
                                HttpServletRequest request){
        String userNo = request.getAttribute("username").toString() ;
        int size = commentService.selectAllComments().size()+1;
        String CommentId = "C"+size;
        int LikesNumber = 0;
        LocalDateTime postingTime = LocalDateTime.now();

        while (true){
            if(commentService.selectByCommentId(CommentId) != null){
                size += 1;
                CommentId = "C" + size;
            }else{
                break;
            }
        }

        String message;
        try {
            commentService.insertComment(CommentId,discussionId,commentInfo,
                    LikesNumber,postingTime,imgUrl,mentionedUser,userNo);
            message = "评论成功！";
        }catch (Exception e){
            message = "评论失败，请重试！";
            return Result.error(message);
        }
        return Result.success(message);
    }

    /**
     *  删除指定帖子下面的评论
     */
    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    public Result deleteComment(@RequestBody Map<String,String> requestData ,HttpServletRequest request){
        String username = request.getAttribute("username").toString();
        if (username.startsWith("T")){
            String CommentId = requestData.get("commentId");
            String message ;
            try {
                commentService.deleteComment(CommentId);
                message = "删除评论成功！";
            }catch (Exception e){
                message = "删除评论失败，请重试！";
                return Result.error(message);
            }
            return Result.success(message);
        }else{
            String log = "无权限操作，请联系管理员";
            return Result.error(log);
        }

    }

    /**
     *  更新评论信息
     */
    @RequestMapping(value = "/updateInfo" , method = RequestMethod.POST)
    public Result updateInfo(@RequestBody Map<String,String> requestData){
        String newInfo = requestData.get("updateInfo");
        String commentId = requestData.get("commentId");
        String message ;
        try {
            commentService.updateCommentInfo(newInfo,commentId);
        }catch (Exception e){
            message = "修改失败，请重新尝试";
            return Result.error(message);
        }
        message = "修改成功！";
        return Result.success(message);
    }

    /**
     *  给评论点赞
     */
    @RequestMapping(value = "/increaseLikes" , method = RequestMethod.POST)
    public Result increaseLikes(@RequestBody Map<String,String> requestData){
        String commentId = requestData.get("commentId");
        Comment c = commentService.selectByCommentId(commentId);
        int likes = c.getLikesNumber();

        String message;
        try{
            commentService.updateCommentLikes(likes+1,commentId);
            message = "点赞成功！";
        }catch (Exception e){
            message = "点赞失败，请重新尝试";
            return Result.error(message);
        }
       return Result.success(message);
    }

    /**
     *  删除点赞
     */
    @RequestMapping(value = "/deleteLikes" , method = RequestMethod.POST)
    public Result deleteLikes(@RequestBody Map<String,String> requestData){
        String commentId = requestData.get("commentId");
        Comment c = commentService.selectByCommentId(commentId);
        int likes = c.getLikesNumber();
        String message;
        try{
            if(likes - 1 < 0){
                message = "点赞不能为负";
                return Result.error(message);
            }else{
                commentService.updateCommentLikes(likes-1,commentId);
                message = "点赞成功！";
            }
        }catch (Exception e){
            message = "点赞失败，请重新尝试";
            return Result.error(message);
        }
        return Result.success(message);
    }

}
