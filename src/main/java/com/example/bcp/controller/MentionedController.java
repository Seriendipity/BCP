package com.example.bcp.controller;

import com.example.bcp.entity.Comment;
import com.example.bcp.entity.Mentioned;
import com.example.bcp.entity.Result;
import com.example.bcp.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mentioned")
public class MentionedController {
    @Autowired
    private MentionedService mentionedService;
    @Autowired
    private DiscussionService discussionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TeachingService teachingService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;


    @GetMapping("/all")
    public Result getAll(HttpServletRequest request){
        String username = request.getAttribute("username").toString();
        List<Mentioned> mentioneds = mentionedService.selectAllMentioned(username);
        Map<String,Object> responseData = new HashMap<>();
        int index = 1;
        for(Mentioned m : mentioneds ){
            Map<String,Object> mentioned = new HashMap<>();
            //cid，帖子内容，发帖人，课程名，状态， Did
            String commentId = m.getCommentId();
            String discussionId = commentService.selectByCommentId(commentId).getDiscussionId();
            String cid = discussionService.selectByDiscussionId(discussionId).getCid();
            String courseNo = teachingService.selectByCid(cid).getCourseNo();
            String courseName = courseService.selectByCourseNo(courseNo).getCourseName() ;
            String postNo = commentService.selectByCommentId(commentId).getPostStudent();
            String postName = studentService.selectByStudentNo(postNo).getStudentName() ;
            mentioned.put("commentId",commentId);
            mentioned.put("discussionId",discussionId);
            mentioned.put("cid",cid);
            mentioned.put("courseName",courseName);
            mentioned.put("postName",postName);
            mentioned.put("status",m.getStatus());
            mentioned.put("discussionInfo",discussionService.selectByDiscussionId(discussionId).getDiscussionInformation());
            responseData.put("Mentioned"+index,mentioned);
            index++;
        }
        return Result.success(responseData);
    }

    @PostMapping("/update")
    public Result updateStatus(HttpServletRequest request,
                               @RequestParam String commentId){
        String username = request.getAttribute("username").toString();
        try{
            String discussionId = commentService.selectByCommentId(commentId).getDiscussionId();
            List<Comment> comments = commentService.selectByDiscussionId(discussionId);
            for(Comment comment:comments){
                mentionedService.updateStatus(comment.getCommentId(),username);
            }
            return Result.success("修改成功");
        }catch (Exception e){
            return Result.error("更新状态失败");
        }

    }
}
