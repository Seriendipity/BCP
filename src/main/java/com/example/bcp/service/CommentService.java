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

    public List<Comment> mentionedComment(String StudentNo,String Cid){
        return commentMapper.mentionedComment(StudentNo,Cid);
    }
    public List<Comment> selectByDiscussionId(String DiscussionId){
        return commentMapper.selectByDiscussionId(DiscussionId);
    }

    public Comment selectByCommentId(String CommentId){
        return commentMapper.selectByCommentId(CommentId);
    }

    public void insertComment(String CommentId , String DiscussionId ,
                              String CommentInformation , int LikesNumber ,
                              LocalDateTime CommentPostingTime,String imgUrl,
                              String mentionedUser,String postStudent){
        commentMapper.insertComment(CommentId,DiscussionId,CommentInformation,
                LikesNumber, CommentPostingTime,imgUrl,mentionedUser,postStudent);
    }

    public int commentTimes(String DiscussionId){
        return commentMapper.commentTimes(DiscussionId);
    }
    public void deleteComment(String CommentId){
        commentMapper.deleteComment(CommentId);
    }

    public void updateCommentLikes(int LikesNumber,String CommentId){
        commentMapper.updateCommentLikes(LikesNumber,CommentId);
    }

    public void updateCommentInfo(String CommentInformation,String CommentId){
        commentMapper.updateCommentInfo(CommentInformation,CommentId);
    }

    public List<Comment> fuzzyQuery(String SelectInfo,String DiscussionId){
        return commentMapper.fuzzyQuery(SelectInfo,DiscussionId);
    }
}
