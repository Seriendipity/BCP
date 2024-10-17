package com.example.bcp.mapper;

import com.example.bcp.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("Select * from Comment")
    List<Comment> selectAllComments();

    @Select("Select * from Comment Where DiscussionId = #{DiscussionId}")
    List<Comment> selectByDiscussionId(String DiscussionId);

    @Select("Select * from Comment Where CommentId = #{CommentId}")
    Comment selectByCommentId(String CommentId);

    @Insert("Insert into Comment(CommentId,DiscussionId,CommentInformation,LikesNumber,CommentPostingTime)"+
    " values(#{CommentId},#{DiscussionId},#{CommentInformation} , #{LikesNumber} , #{CommentPostingTime})")
    void insertComment(String CommentId , String DiscussionId ,
                       String CommentInformation , int LikesNumber ,
                       LocalDateTime CommentPostingTime);


    @Delete("Delete from Comment Where CommentId = #{CommentId}")
    void deleteComment(String CommentId);

    @Update("Update Comment Set LikesNumber = #{LikesNumber} where CommentId = #{CommentId}")
    void updateCommentLikes(int LikesNumber,String CommentId);

    @Update("Update Comment Set CommentInformation = #{CommentInformation} , CommentPostingTime = Now() where CommentId = #{CommentId}")
    void updateCommentInfo(String CommentInformation,String CommentId);
}
