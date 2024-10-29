package com.example.bcp.controller;

import com.example.bcp.entity.Comment;
import com.example.bcp.entity.Discussion;
import com.example.bcp.entity.Result;
import com.example.bcp.service.DiscussionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/discussion")
public class
DiscussionController {
    @Autowired
    private DiscussionService discussionService;

    /**
    * 查询某门课程的所有帖子
    * */
    @GetMapping(value = "/all" )
    public Result getAllDiscussion(@RequestParam String cid, HttpServletRequest request){
        List<Discussion> discussions = discussionService.selectByCid(cid);
        String username = request.getAttribute("username").toString();

        Map<String, Object> responseData = new HashMap<>();
        int i = 1;
        for(Discussion d : discussions){
            Map<String , Object> discussionInfo = new HashMap<>();
            discussionInfo.put("index",i);
            discussionInfo.put("DiscussionId",d.getDiscussionId());
            discussionInfo.put("Information",d.getDiscussionInformation());
            discussionInfo.put("PostingTime",d.getDiscussionPostingTime());
            discussionInfo.put("studentNo",d.getStudentNo());
            discussionInfo.put("imgUrl",d.getImageUrl());
            discussionInfo.put("mentionedUser",d.getMentionedUser());
            discussionInfo.put("topic",d.getTopic());
            responseData.put("discussion" + i,discussionInfo);
            i++;
        }
        responseData.put("username", username);
        return Result.success(responseData);
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
        for(Discussion d : discussions){
            Map<String , Object> discussionInfo = new HashMap<>();
            discussionInfo.put("index",i);
            discussionInfo.put("DiscussionId",d.getDiscussionId());
            discussionInfo.put("Information",d.getDiscussionInformation());
            discussionInfo.put("PostingTime",d.getDiscussionPostingTime());
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
        for(Discussion d : discussions){
            Map<String , Object> discussionInfo = new HashMap<>();
            discussionInfo.put("index",i);
            discussionInfo.put("DiscussionId",d.getDiscussionId());
            discussionInfo.put("Information",d.getDiscussionInformation());
            discussionInfo.put("PostingTime",d.getDiscussionPostingTime());
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
        int i = 1;
        for(Discussion d : discussions){
            Map<String , Object> discussionInfo = new HashMap<>();
            discussionInfo.put("index",i);
            discussionInfo.put("DiscussionId",d.getDiscussionId());
            discussionInfo.put("Information",d.getDiscussionInformation());
            discussionInfo.put("PostingTime",d.getDiscussionPostingTime());
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
        int i = 1;
        for(Discussion d : discussions){
            Map<String , Object> discussionInfo = new HashMap<>();
            discussionInfo.put("index",i);
            discussionInfo.put("DiscussionId",d.getDiscussionId());
            discussionInfo.put("Information",d.getDiscussionInformation());
            discussionInfo.put("PostingTime",d.getDiscussionPostingTime());
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
    @RequestMapping(value = "/insert" , method = RequestMethod.POST)
    public Result insertDiscussion(@RequestBody Map<String,String> requestData){
        String cid = requestData.get("cid");
        String StudentNo = requestData.get("studentNo");
        String DiscussionInfo = requestData.get("information");
        String mentionedUser = requestData.get("mentionedUser");
        String imgUrl = requestData.get("imgUrl");
        String tag = requestData.get("tag");
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
    public Result deleteDiscussion(@RequestBody Map<String,String> requestData,HttpServletRequest request){
       String username = request.getAttribute("username").toString();
       if (username.startsWith("T")){
           String DiscussionId =requestData.get("discussionId");
           String message ;
           try{
               discussionService.deleteDiscussion(DiscussionId);
               message = "帖子删除成功！";
           }catch (Exception e){
               message = "帖子删除失败，请重试";
               return Result.error(message);
           }
           return Result.success(message);
       }else {
           String log = "无删除权限";
           return Result.error(log);
       }

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
