package com.example.bcp.mapper;

import com.example.bcp.entity.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("Select * from Comment")
    List<Comment> selectAllComments();

    @Select("Select * from Comment Where DiscussionId = #{DiscussionId}")
    List<Comment> selectByDiscussionId(String DiscussionId);

    @Insert("Insert into Comment(CommentId,DiscussionId,CommentInformation,LikesNumber,CommentPostingTime)"+
    " values(#{CommentId},#{DiscussionId},#{CommentInformation} , #{LikesNumber} , #{CommentPostingTime})")
    void insertComment(String CommentId , String DiscussionId ,
                       String CommentInformation , int LikesNumber ,
                       LocalDateTime CommentPostingTime);


    @Delete("Delete from Comment Where CommentId = #{CommentId}")
    void deleteComment(String CommentId);
}
