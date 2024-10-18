package com.example.bcp.mapper;

import com.example.bcp.entity.Assistant;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssistantMapper {
    @Select("Select * from Assistant")
    List<Assistant> selectAllAssistant();

    @Select("Select * from Assistant Where Cid = #{Cid}")
    List<Assistant> selectByCid(String Cid);

    @Select("Select * from Assistant Where AssistantNo = #{AssistantNo}")
    Assistant selectByAssistantNo(String AssistantNo);

    @Insert("Insert into Assistant(AssistantNo,Cid)" +
            " values(#{AssistantNo} , #{Cid})")
    void insertAssistant(String AssistantNo,String Cid);

    @Delete("Delete from Assistant Where AssistantNo = #{AssistantNo}")
    void deleteAssistant(String AssistantNo);

    @Update("Update Assistant Set Password = #{Password} Where AssistantNo = #{AssistantNo}")
    void updateAssistantPassword(String Password , String AssistantNo);

    @Update("Update Assistant Set PicturePath = #{PicturePath} Where AssistantNo = #{AssistantNo}")
    void updateAssistantPicturePath(String PicturePath, String AssistantNo);

}
