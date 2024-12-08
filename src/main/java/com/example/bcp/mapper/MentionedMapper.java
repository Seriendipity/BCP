package com.example.bcp.mapper;

import com.example.bcp.entity.Mentioned;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MentionedMapper {
    @Select("Select * from mentioned WHere Status = 0 and MentionedUser Like CONCAT('%', #{StudentNo}, '%')")
    List<Mentioned> selectAllMentioned(String StudentNo);

    @Insert("Insert into mentioned(CommentId,MentionedUser,Status) Values(#{CommentId},#{MentionedUser},#{Status})")
    void insertMentioned(String CommentId,String MentionedUser,Boolean Status);

    @Update("Update mentioned set Status = 1 Where CommentId = #{CommentId} and MentionedUser = #{MentionedUser}")
    void updateStatus(String CommentId,String MentionedUser);
}
