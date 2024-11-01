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

    public Discussion selectByDiscussionId(String DiscussionId){
        return discussionMapper.selectByDiscussionId(DiscussionId);
    }

    public List<Discussion> selectByCid(String Cid){
        return discussionMapper.selectByCid(Cid);
    }

    public List<Discussion> selectByCidAndStudentNo(String StudentNo , String Cid){
        return discussionMapper.selectByCidAndStudentNo(StudentNo,Cid);
    }

    public void insertDiscussion(String StudentNo, String Cid, String DiscussionId,
                            String DiscussionInformation, LocalDateTime DiscussionPostingTime,
                            String mentionedUser, String imgUrl, String tags){
        discussionMapper.insertDiscussion(StudentNo,Cid,DiscussionId,DiscussionInformation,
                                DiscussionPostingTime,mentionedUser,imgUrl,tags);
    }

    public void deleteDiscussion(String DiscussionId){
        discussionMapper.deleteDiscussion(DiscussionId);
    }

    public void updateDiscussionInfo(String DiscussionInformation,String DiscussionId){
        discussionMapper.updateDiscussionInfo(DiscussionInformation,DiscussionId);
    }

    public List<Discussion> selectByCidAndTopic(String topic,String Cid){
        return discussionMapper.selectByCidAndTopic(topic,Cid);
    }

    public List<Discussion> selectByCidAndMentioned(String StudentNo,String Cid){
        return discussionMapper.selectByCidAndMentioned(StudentNo,Cid);
    }

    public List<Discussion> selectByMentioned(String StudentNo){
        return discussionMapper.selectByMentioned(StudentNo);
    }

    public List<Discussion> fuzzyQuery(String SelectInfo,String Cid){
        return discussionMapper.fuzzyQuery(SelectInfo,Cid);
    }
}
