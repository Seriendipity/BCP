package com.example.bcp.mapper;

import com.example.bcp.entity.PeerReviewAssignment;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
@Mapper
public interface PeerReviewAssignmentMapper {
    @Insert("INSERT INTO PeerReviewAssignment (RevieweeNo, ReviewerNo, HomeworkNo, StartTime, EndTime) " +
            "VALUES (#{revieweeNo}, #{reviewerNo}, #{homeworkNo}, #{startTime}, #{endTime})")
    void insertPeerReviewAssignment(String revieweeNo, String reviewerNo, String homeworkNo, LocalDateTime startTime, LocalDateTime endTime);

    // 更新评审状态
    @Update("UPDATE PeerReviewAssignment SET ReviewStatus = #{reviewStatus} WHERE RevieweeNo = #{revieweeNo} AND ReviewerNo = #{reviewerNo} AND HomeworkNo = #{homeworkNo}")
    void updateReviewStatus(boolean reviewStatus, String revieweeNo, String reviewerNo, String homeworkNo);

    @Update("UPDATE PeerReviewAssignment SET Grade = #{grade}, Comment = #{comment} WHERE RevieweeNo = #{revieweeNo} AND ReviewerNo = #{reviewerNo} AND HomeworkNo = #{homeworkNo}")
    void updateGradeAndComment(int grade, String comment, String revieweeNo,String reviewerNo, String homeworkNo);


    // 根据条件查询评审分配
    @Select("SELECT * FROM PeerReviewAssignment WHERE RevieweeNo = #{revieweeNo} AND ReviewerNo = #{reviewerNo} AND HomeworkNo = #{homeworkNo}")
    PeerReviewAssignment selectByPeerAndHomework(String revieweeNo,String reviewerNo,String homeworkNo);

    @Select("SELECT * FROM PeerReviewAssignment WHERE RevieweeNo = #{revieweeNo} AND HomeworkNo = #{homeworkNo}")
    List<PeerReviewAssignment> selectByRevieweeAndHomework(String revieweeNo,String homeworkNo);

    @Select("SELECT * FROM PeerReviewAssignment WHERE RevieweeNo = #{revieweeNo}")
    List<PeerReviewAssignment> selectByRevieweeNo(String revieweeNo);

    @Select("SELECT * FROM PeerReviewAssignment WHERE HomeworkNo = #{homeworkNo}")
    List<PeerReviewAssignment> selectByHomeworkNo(String homeworkNo);

    @Select("SELECT * FROM PeerReviewAssignment WHERE ReviewerNo = #{reviewerNo} AND HomeworkNo = #{homeworkNo}")
    List<PeerReviewAssignment> selectByReviewerAndHomework(String reviewerNo,String homeworkNo);

    @Select("SELECT * FROM PeerReviewAssignment WHERE ReviewerNo = #{reviewerNo}")
    List<PeerReviewAssignment> selectByReviewerNo(String reviewerNo);

    // 查询所有评审分配
    @Select("SELECT * FROM PeerReviewAssignment")
    List<PeerReviewAssignment> selectAllAssignment();

    // 根据条件删除评审分配
    @Delete("DELETE FROM PeerReviewAssignment WHERE RevieweeNo = #{revieweeNo} AND ReviewerNo = #{reviewerNo} AND HomeworkNo = #{homeworkNo}")
    int deleteByPeerAndHomework(String revieweeNo,String reviewerNo,String homeworkNo);

    @Delete("DELETE FROM PeerReviewAssignment WHERE HomeworkNo = #{homeworkNo}")
    int deleteByHomework(String homeworkNo);
}
