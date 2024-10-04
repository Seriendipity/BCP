package com.example.bcp.service;

import com.example.bcp.entity.Discussion;
import com.example.bcp.mapper.DiscussionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiscussionService {
    @Autowired
    private DiscussionMapper discussionMapper;

    public List<Discussion> selectAllDiscussion(){
        return discussionMapper.selectAllDiscussion();
    }

    public List<Discussion> selectByStudentNo(String StudentNo){
        return discussionMapper.selectByStudentNo(StudentNo);
    }

    public List<Discussion> selectByCid(String Cid){
        return discussionMapper.selectByCid(Cid);
    }

    public List<Discussion> selectByCidAndStudentNo(String StudentNo , String Cid){
        return discussionMapper.selectByCidAndStudentNo(StudentNo,Cid);
    }

    public void insertDiscussion(String StudentNo , String Cid ,
                                 String DiscussionId , String DiscussionInformation ,
                                 LocalDateTime DiscussionPostingTime){
        discussionMapper.insertDiscussion(StudentNo,Cid,DiscussionId,
                DiscussionInformation,DiscussionPostingTime);
    }

    public void deleteDiscussion(String DiscussionId){
        discussionMapper.deleteDiscussion(DiscussionId);
    }
}
