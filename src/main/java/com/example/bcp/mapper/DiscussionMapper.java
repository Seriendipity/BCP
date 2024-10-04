package com.example.bcp.mapper;

import com.example.bcp.entity.Discussion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    @Select("Select * from Discussion Where StudentNo = #{StudentNo} and Cid = #{Cid}")
    List<Discussion> selectByCidAndStudentNo(String StudentNo , String Cid);

    @Insert("Insert into Discussion(StudentNo,Cid,DiscussionId,DiscussionInformation,DiscussionPostingTime)"+
    " values(#{StudentNo} , #{Cid} , #{DiscussionId} , #{DiscussionInformation} , #{DiscussionPostingTime})")
    void insertDiscussion(String StudentNo , String Cid ,
                          String DiscussionId , String DiscussionInformation,
                          LocalDateTime DiscussionPostingTime);

    @Delete("Delete From Discussion Where DiscussionId = #{DiscussionId}")
    void deleteDiscussion(String DiscussionId);
}
