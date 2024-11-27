package com.example.bcp.controller;

import com.example.bcp.entity.*;
import com.example.bcp.service.CommentService;
import com.example.bcp.service.DiscussionService;
import com.example.bcp.service.FavoriteService;
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
@RequestMapping("/discussion")
public class
DiscussionController {
    @Autowired
    private DiscussionService discussionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private FavoriteService favoriteService;

    /**
    * 查询某门课程的所有帖子
    * */
    @GetMapping(value = "/all" )
    public Result getAllDiscussion(@RequestParam String cid, HttpServletRequest request){
        List<Discussion> discussions = discussionService.selectByCid(cid);
        String username = request.getAttribute("username").toString();

        Map<String, Object> responseData = new HashMap<>();
        int i = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for(Discussion d : discussions){
            Map<String , Object> discussionInfo = new HashMap<>();
            discussionInfo.put("index",i);
            discussionInfo.put("DiscussionId",d.getDiscussionId());
            discussionInfo.put("Information",d.getDiscussionInformation());
            discussionInfo.put("PostingTime",d.getDiscussionPostingTime().format(formatter));
            discussionInfo.put("studentNo",d.getStudentNo());
            discussionInfo.put("username",studentService.selectByStudentNo(d.getStudentNo()).getStudentName());
            discussionInfo.put("imgUrl",d.getImageUrl());
            discussionInfo.put("mentionedUser",d.getMentionedUser());
            discussionInfo.put("topic",d.getTopic());
            discussionInfo.put("starTimes",d.getStarTimes());
            discussionInfo.put("commentTimes",commentService.commentTimes(d.getDiscussionId()));
            responseData.put("discussion" + i,discussionInfo);
            i++;
        }
        return Result.success(responseData);
    }

    /**
     *  查询某个帖子的信息
     */
    @GetMapping(value = "/getOneDiscussion" )
    public Result getOneDiscussion(@RequestParam String discussionId){
        System.out.println(12345);
        System.out.println(discussionId);
        Discussion d = discussionService.selectByDiscussionId(discussionId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String , Object> discussionInfo = new HashMap<>();
        discussionInfo.put("discussionId",d.getDiscussionId());
        discussionInfo.put("Information",d.getDiscussionInformation());
        discussionInfo.put("PostingTime",d.getDiscussionPostingTime().format(formatter));
        discussionInfo.put("studentNo",d.getStudentNo());
        discussionInfo.put("username",studentService.selectByStudentNo(d.getStudentNo()).getStudentName());
        discussionInfo.put("imgUrl",d.getImageUrl());
        discussionInfo.put("mentionedUser",d.getMentionedUser());
        discussionInfo.put("topic",d.getTopic());
        discussionInfo.put("starTimes",d.getStarTimes());
        discussionInfo.put("commentTimes",commentService.commentTimes(d.getDiscussionId()));
        return Result.success(discussionInfo);
    }

    /**
     * 查询某个学生某门课程的所有帖子
     * */
    @GetMapping(value = "/student_all")
    public Result getStudentAllDiscussion(@RequestParam String cid, HttpServletRequest request) {
        String studentNo = request.getAttribute("username").toString();
        List<Discussion> discussions = discussionService.selectByCidAndStudentNo(studentNo,cid);
        Map<String, Object> responseData = new HashMap<>();
        int i = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for(Discussion d : discussions){
            Map<String , Object> discussionInfo = new HashMap<>();
            discussionInfo.put("index",i);
            discussionInfo.put("DiscussionId",d.getDiscussionId());
            discussionInfo.put("Information",d.getDiscussionInformation());
            discussionInfo.put("PostingTime",d.getDiscussionPostingTime().format(formatter));
            discussionInfo.put("studentNo",d.getStudentNo());
            discussionInfo.put("imgUrl",d.getImageUrl());
            discussionInfo.put("mentionedUser",d.getMentionedUser());
            discussionInfo.put("topic",d.getTopic());
            responseData.put("discussion" + i,discussionInfo);
            i++;
        }
        responseData.put("username", request.getAttribute("username").toString());
        return Result.success(responseData);
    }

    /**
     *  查找指定课程下某一话题的所有帖子
     */
    @GetMapping(value = "/topic")
    public Result getDiscussionWithTopic(@RequestParam String cid,
                                         @RequestParam String topic,
                                         HttpServletRequest request){
        List<Discussion> discussions = discussionService.selectByCidAndTopic(topic,cid);
        Map<String, Object> responseData = new HashMap<>();
        int i = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for(Discussion d : discussions){
            Map<String , Object> discussionInfo = new HashMap<>();
            discussionInfo.put("index",i);
            discussionInfo.put("DiscussionId",d.getDiscussionId());
            discussionInfo.put("Information",d.getDiscussionInformation());
            discussionInfo.put("PostingTime",d.getDiscussionPostingTime().format(formatter));
            discussionInfo.put("studentNo",d.getStudentNo());
            discussionInfo.put("imgUrl",d.getImageUrl());
            discussionInfo.put("mentionedUser",d.getMentionedUser());
            discussionInfo.put("topic",d.getTopic());
            responseData.put("discussion" + i,discussionInfo);
            i++;
        }
        return Result.success(responseData);
    }

    /**
     *  查找指定课程下某一课程下，被别人@的所有帖子
     */
    @GetMapping(value = "/cid_mentioned")
    public Result getMentionedDiscussionWithCid(@RequestParam String cid,
                                                HttpServletRequest request){
        String username = request.getAttribute("username").toString();
        List<Discussion> discussions = discussionService.selectByCidAndMentioned(username,cid);
        Map<String, Object> responseData = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int i = 1;
        for(Discussion d : discussions){
            Map<String , Object> discussionInfo = new HashMap<>();
            discussionInfo.put("index",i);
            discussionInfo.put("DiscussionId",d.getDiscussionId());
            discussionInfo.put("Information",d.getDiscussionInformation());
            discussionInfo.put("PostingTime",d.getDiscussionPostingTime().format(formatter));
            discussionInfo.put("studentNo",d.getStudentNo());
            discussionInfo.put("imgUrl",d.getImageUrl());
            discussionInfo.put("mentionedUser",d.getMentionedUser());
            discussionInfo.put("topic",d.getTopic());
            responseData.put("discussion" + i,discussionInfo);
            i++;
        }
        return Result.success(responseData);
    }

    /**
     *  查找所有被别人@的所有帖子
     */
    @GetMapping("/mentioned")
    public Result getMentionedDiscussion(HttpServletRequest request){
        String username = request.getAttribute("username").toString();
        List<Discussion> discussions = discussionService.selectByMentioned(username);
        Map<String, Object> responseData = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int i = 1;
        for(Discussion d : discussions){
            Map<String , Object> discussionInfo = new HashMap<>();
            discussionInfo.put("index",i);
            discussionInfo.put("DiscussionId",d.getDiscussionId());
            discussionInfo.put("Information",d.getDiscussionInformation());
            discussionInfo.put("PostingTime",d.getDiscussionPostingTime().format(formatter));
            discussionInfo.put("studentNo",d.getStudentNo());
            discussionInfo.put("imgUrl",d.getImageUrl());
            discussionInfo.put("mentionedUser",d.getMentionedUser());
            discussionInfo.put("topic",d.getTopic());
            responseData.put("discussion" + i,discussionInfo);
            i++;
        }
        return Result.success(responseData);
    }

    /**
     *  新增帖子
     */
    @PostMapping(value = "/insert")
    public Result insertDiscussion(@RequestParam String cid,
                                   @RequestParam String information,
                                   @RequestParam String mentionedUser,
                                   @RequestParam String imgUrl,
                                   @RequestParam String tag,
                                   HttpServletRequest request){
        String StudentNo = request.getAttribute("username").toString();
        String DiscussionInfo =information;
        System.out.println(DiscussionInfo);
        LocalDateTime postingTime  = LocalDateTime.now();
        int size = discussionService.selectAllDiscussion().size()+1;
        String DiscussionId = "D"+size;
        while (true){
            if(discussionService.selectByDiscussionId(DiscussionId) != null){
                size += 1;
                DiscussionId = "D" +size;
            }else{
                break;
            }
        }
        String message ;
        System.out.println(DiscussionId);
        try{
            discussionService.insertDiscussion(StudentNo,cid,DiscussionId,
                    DiscussionInfo,postingTime,mentionedUser,imgUrl,tag);
            message = "帖子新建成功！";
        }catch (Exception e){
            message = "帖子创建失败，请重试";
            return Result.error(message);
        }
        return Result.success(message);
    }

    /**
     *  删除帖子
     */
    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    public Result deleteDiscussion(@RequestParam String discussionId,HttpServletRequest request){
       String username = request.getAttribute("username").toString();

           String message ;
           try{
               List<Favorite> favorites = favoriteService.selectByFavoriteInformationNo(discussionId);
               List<Comment> comments = commentService.selectByDiscussionId(discussionId);
               for(Favorite f : favorites){
                   favoriteService.deleteFavorite(f.getFavoriteNo(),f.getStudentNo(),discussionId);
               }
               for(Comment c : comments){
                   commentService.deleteComment(c.getCommentId());
               }
               discussionService.deleteDiscussion(discussionId);
               message = "帖子删除成功！";
           }catch (Exception e){
               message = "帖子删除失败，请重试";
               return Result.error(message);
           }
           return Result.success(message);

    }

    /**
     *  修改帖子的信息
     */
    @RequestMapping(value = "/updateInfo" , method = RequestMethod.POST)
    public Result updateDiscussionInfo(@RequestBody Map<String,String> requestData){
        String DiscussionInfo = requestData.get("discussionInfo");
        String DiscussionId = requestData.get("discussionId");
        String message;
        try{
            discussionService.updateDiscussionInfo(DiscussionInfo,DiscussionId);
            message = "修改帖子成功！";
        }catch (Exception e){
            message = "帖子修改失败，请重试";
            return Result.error(message);
        }
        return Result.success(message);
    }

}
