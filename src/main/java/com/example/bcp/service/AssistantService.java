package com.example.bcp.service;

import com.example.bcp.entity.Assistant;
import com.example.bcp.mapper.AssistantMapper;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssistantService {
    @Autowired
    private AssistantMapper assistantMapper;

    public List<Assistant> selectAllAssistant(){
        return assistantMapper.selectAllAssistant();
    }

    public List<Assistant> selectByCid(String Cid){
        return assistantMapper.selectByCid(Cid);
    }

    public void insertAssistant(String AssistantNo,String Cid){
        assistantMapper.insertAssistant(AssistantNo,Cid);
    }

    public void deleteAssistant(String AssistantNo){
        assistantMapper.deleteAssistant(AssistantNo);
    }


}
