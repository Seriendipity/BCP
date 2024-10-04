package com.example.bcp.mapper;

import com.example.bcp.entity.Assistant;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface AssistantMapper {
    @Select("Select * from Assistant")
    List<Assistant> selectAllAssistant();

    @Select("Select * from Assistant Where Cid = #{Cid}")
    List<Assistant> selectByCid(String Cid);

    @Insert("Insert into Assistant(AssistantNo,Cid)" +
            " values(#{AssistantNo} , #{Cid})")
    void insertAssistant(String AssistantNo,String Cid);

    @Delete("Delete from Assistant Where AssistantNo = #{AssistantNo}")
    void deleteAssistant(String AssistantNo);
}
