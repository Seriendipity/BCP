package com.example.bcp.mapper;

import com.example.bcp.entity.PeerReviewAssignment;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface PeerReviewAssignmentMapper {
    @Insert("INSERT INTO PeerReviewAssignment (RevieweeNo, ReviewerNo, HomeworkNo, StartTime, EndTime) " +
            "VALUES (#{revieweeNo}, #{reviewerNo}, #{homeworkNo}, #{startTime}, #{endTime})")
    void insertPeerReviewAssignment(String revieweeNo, String reviewerNo, String homeworkNo, String startTime, String endTime);

    // 更新评审状态
    @Update("UPDATE PeerReviewAssignment SET ReviewStatus = #{reviewStatus} WHERE RevieweeNo = #{revieweeNo} AND ReviewerNo = #{reviewerNo} AND HomeworkNo = #{homeworkNo}")
    void updateReviewStatus(boolean reviewStatus, String revieweeNo, String reviewerNo, String homeworkNo);


    // 根据条件查询评审分配
    @Select("SELECT * FROM PeerReviewAssignment WHERE RevieweeNo = #{revieweeNo} AND ReviewerNo = #{reviewerNo} AND HomeworkNo = #{homeworkNo}")
    PeerReviewAssignment selectByPeerAndHomework(String revieweeNo,String reviewerNo,String homeworkNo);

    @Select("SELECT * FROM PeerReviewAssignment WHERE RevieweeNo = #{revieweeNo}")
    PeerReviewAssignment selectByRevieweeNo(String revieweeNo);

    // 查询所有评审分配
    @Select("SELECT * FROM PeerReviewAssignment")
    List<PeerReviewAssignment> selectAllAssignment();

    // 根据条件删除评审分配
    @Delete("DELETE FROM PeerReviewAssignment WHERE RevieweeNo = #{revieweeNo} AND ReviewerNo = #{reviewerNo} AND HomeworkNo = #{homeworkNo}")
    int deleteByPeerAndHomework(String revieweeNo,String reviewerNo,String homeworkNo);
}
