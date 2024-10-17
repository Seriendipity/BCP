package com.example.bcp.controller;


import com.example.bcp.entity.Comment;
import com.example.bcp.entity.Result;
import com.example.bcp.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 查询某个帖子的所有评论
     * */
    @RequestMapping(value = "/all" , method = RequestMethod.POST)
    public Result<List<Comment>> getAllComments(@RequestBody Map<String,String> requestData){
        System.out.println(1);
        String discussionId = requestData.get("discussionId");
        List<Comment> comments = commentService.selectByDiscussionId(discussionId);
        return Result.success(comments);
    }


    @RequestMapping(value = "/insert" , method = RequestMethod.POST)
    public Result insertComment(@RequestBody Map<String,String> requestData){
        int size = commentService.selectAllComments().size()+1;
        String CommentId = "C"+size;
        String DiscussionId = requestData.get("discussionId");
        String CommentInfo = requestData.get("commentInfo");
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
            commentService.insertComment(CommentId,DiscussionId,CommentInfo,
                    LikesNumber,postingTime);
            message = "评论成功！";
        }catch (Exception e){
            message = "评论失败，请重试！";
            return Result.error(message);
        }
        return Result.success(message);
    }

    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    public Result deleteComment(@RequestBody Map<String,String> requestData){
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
    }


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
