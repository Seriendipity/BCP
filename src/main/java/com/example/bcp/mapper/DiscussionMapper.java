package com.example.bcp.mapper;

import com.example.bcp.entity.Discussion;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DiscussionMapper {
    @Select("Select * from Discussion")
    List<Discussion> selectAllDiscussion();

    @Select("Select * from Discussion Where StudentNo = #{StudentNo}")
    List<Discussion> selectByStudentNo(String StudentNo);

    @Select("Select * from Discussion Where Cid = #{Cid}")
    List<Discussion> selectByCid(String Cid);

    @Select("Select * from Discussion Where DiscussionId = #{DiscussionId}")
    Discussion selectByDiscussionId(String DiscussionId);

    @Select("Select * from Discussion Where StudentNo = #{StudentNo} and Cid = #{Cid}")
    List<Discussion> selectByCidAndStudentNo(String StudentNo , String Cid);

    @Select("Select * from Discussion Where topic = #{topic} and Cid = #{Cid}")
    List<Discussion> selectByCidAndTopic(String topic,String Cid);

    @Select("Select * from Discussion Where mentionedUser like concat('%', #{StudentNo}, '%') and Cid = #{Cid}")
    List<Discussion> selectByCidAndMentioned(String StudentNo,String Cid);

    @Select("Select * from Discussion Where mentionedUser like concat('%',#{StudentNo},'%')")
    List<Discussion> selectByMentioned(String StudentNo);

    @Select("Select * from Discussion Where DiscussionInformation Like concat('%', #{SelectInfo}, '%') and Cid = #{Cid} ")
    List<Discussion> fuzzyQuery(String SelectInfo,String Cid);

    @Insert("INSERT INTO Discussion (StudentNo, Cid, DiscussionId, DiscussionInformation, DiscussionPostingTime, mentionedUser, imgUrl, topic) " +
            "VALUES (#{StudentNo}, #{Cid}, #{DiscussionId}, #{DiscussionInformation}, #{DiscussionPostingTime}, #{mentionedUser}, #{imgUrl}, #{topic})")
    void insertDiscussion(String StudentNo, String Cid, String DiscussionId,
                          String DiscussionInformation, LocalDateTime DiscussionPostingTime,
                          String mentionedUser, String imgUrl, String topic);


    @Delete("Delete From Discussion Where DiscussionId = #{DiscussionId}")
    void deleteDiscussion(String DiscussionId);

    @Update("Update Discussion Set DiscussionInformation = #{DiscussionInformation} ,DiscussionPostingTime = Now()" +
            "Where DiscussionId = #{DiscussionId}")
    void updateDiscussionInfo(String DiscussionInformation,String DiscussionId);

    @Update("Update Discussion Set starTimes = starTimes+1 Where DiscussionId = #{DiscussionId}")
    void increaseStarTimes(String DiscussionId);
}
