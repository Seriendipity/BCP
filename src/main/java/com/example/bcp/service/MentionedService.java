package com.example.bcp.service;

import com.example.bcp.entity.Mentioned;
import com.example.bcp.mapper.MentionedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentionedService {
    @Autowired
    MentionedMapper mentionedMapper;
    public List<Mentioned> selectAllMentioned(String StudentNo){
       return mentionedMapper.selectAllMentioned(StudentNo);
    }

   public void insertMentioned(String CommentId,String MentionedUser,Boolean Status){
        mentionedMapper.insertMentioned(CommentId,MentionedUser,Status);
   }

   public void updateStatus(String CommentId,String MentionedUser){
        mentionedMapper.updateStatus(CommentId,MentionedUser);
   }
}
