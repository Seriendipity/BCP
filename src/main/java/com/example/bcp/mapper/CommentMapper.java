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

    @Select("Select * from Comment Where CommentInformation Like concat('%', #{SelectInfo}, '%') and DiscussionId = #{DiscussionId}")
    List<Comment> fuzzyQuery(String SelectInfo,String DiscussionId);

    @Select("SELECT * FROM Comment c, Discussion d " +
            "WHERE c.mentionedUser LIKE CONCAT('%', #{StudentNo}, '%') " +
            "AND c.DiscussionId = d.DiscussionId " +
            "AND d.Cid = #{Cid}")
    List<Comment> mentionedComment(String StudentNo,String Cid);

    @Select("SELECT * FROM Comment c, Discussion d " +
            "WHERE c.mentionedUser LIKE CONCAT('%', #{StudentNo}, '%') " +
            "AND c.DiscussionId = d.DiscussionId ")
    List<Comment> allMentionedComment(String StudentNo);

    @Select("Select count(*) from Comment WHere DiscussionId = #{DiscussionId}  ")
    int commentTimes(String DiscussionId);

    @Insert("Insert into Comment(CommentId,DiscussionId,CommentInformation,LikesNumber,CommentPostingTime,imgUrl,mentionedUser,postStudent)"+
    " values(#{CommentId},#{DiscussionId},#{CommentInformation} , #{LikesNumber} , #{CommentPostingTime},#{imgUrl},#{mentionedUser},#{postStudent})")
    void insertComment(String CommentId , String DiscussionId ,
                       String CommentInformation , int LikesNumber ,
                       LocalDateTime CommentPostingTime,String imgUrl,
                       String mentionedUser,String postStudent);


    @Delete("Delete from Comment Where CommentId = #{CommentId}")
    void deleteComment(String CommentId);

    @Update("Update Comment Set LikesNumber = #{LikesNumber} where CommentId = #{CommentId}")
    void updateCommentLikes(int LikesNumber,String CommentId);

    @Update("Update Comment Set CommentInformation = #{CommentInformation} , CommentPostingTime = Now() where CommentId = #{CommentId}")
    void updateCommentInfo(String CommentInformation,String CommentId);
}
