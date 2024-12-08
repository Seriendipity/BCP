package com.example.bcp.controller;


import com.example.bcp.entity.Comment;
import com.example.bcp.entity.Discussion;
import com.example.bcp.entity.Result;
import com.example.bcp.service.CommentService;
import com.example.bcp.service.DiscussionService;
import com.example.bcp.service.MentionedService;
import com.example.bcp.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private DiscussionService discussionService;
    @Autowired
    private MentionedService mentionedService;

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
            commentInfo.put("commentId", c.getCommentId());
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

        String message = "1";

        String regex = "@([\\w\u4e00-\u9fa5]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(commentInfo);
        String mentionedUserName = "";

        // 查找所有匹配项并拼接用户名
        while (matcher.find()) {
            // 如果不是第一个匹配，先加上逗号分隔符
            if (mentionedUserName.length() > 0) {
                mentionedUserName += ",";  // 使用+进行拼接
            }
            mentionedUserName += matcher.group(1);  // 添加用户名
        }
        System.out.println(mentionedUserName);

        // 第二个正则表达式：匹配学号（以S开头后跟数字）
        String regexs = "S\\d+";
        Pattern patterns = Pattern.compile(regexs);
        Matcher matchers = patterns.matcher(mentionedUserName);

        // 使用String来拼接学号
        String studentIds = "";
        // 查找所有匹配项并拼接学号
        while (matchers.find()) {
            // 如果不是第一个匹配，先加上逗号分隔符
            if (studentIds.length() > 0) {
                studentIds += ",";
            }
            studentIds += matchers.group();  // 使用matchers而不是matcher
        }

        // 输出最终拼接的学号字符串
        System.out.println("提到的学号：" + studentIds);
        try {
            commentService.insertComment(CommentId,discussionId,commentInfo,
                    LikesNumber,postingTime,imgUrl,studentIds,userNo);
            String[] names= studentIds.split(",");
            for(String name : names){
                mentionedService.insertMentioned(CommentId,name,false);
            }
            message = "评论成功！";
        }catch (Exception e){
            message = "评论失败，请重试！";
            return Result.error(message);
        }
        return Result.success(message);
    }



    /**
     * 返回自己被@的所有评论
     * @param cid
     * @param request
     * @return
     */
    @GetMapping("/get_all_cid_mentioned")
    public Result getAllMentioned(@RequestParam String cid,
                                  HttpServletRequest request){
        String username = request.getAttribute("username").toString();
        Map<String,Object> responseData = new HashMap<>();
        List<Comment> comments = commentService.mentionedComment(username,cid);
        int index= 1;
        for(Comment c : comments){
            responseData.put("c"+index,c);
            index++;
        }
        return Result.success(responseData);
    }

    /**
     *  删除指定帖子下面的评论
     */
    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    public Result deleteComment(@RequestParam String commentId ,HttpServletRequest request){
        String username = request.getAttribute("username").toString();

            String CommentId = commentId;
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
