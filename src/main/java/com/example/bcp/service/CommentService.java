package com.example.bcp.service;

import com.example.bcp.entity.Comment;
import com.example.bcp.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> selectAllComments(){
        return commentMapper.selectAllComments();
    }

    public List<Comment> selectByDiscussionId(String DiscussionId){
        return commentMapper.selectByDiscussionId(DiscussionId);
    }

    public void insertComment(String CommentId , String DiscussionId ,
                              String CommentInformation , int LikesNumber ,
                              LocalDateTime CommentPostingTime){
        commentMapper.insertComment(CommentId,DiscussionId,CommentInformation,
                LikesNumber, CommentPostingTime);
    }

    public void deleteComment(String CommentId){
        commentMapper.deleteComment(CommentId);
    }
}
