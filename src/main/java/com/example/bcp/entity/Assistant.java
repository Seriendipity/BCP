package com.example.bcp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Assistant")
public class Assistant {
    @Id
    @Column(name = "AssistantNo" , length = 8)
    private String AssistantNo;

    @Column(name = "Cid" ,length = 10)
    private String Cid;

    @Column(name = "password" , length = 20)
    private String password="password123";

    public String getAssistantNo() {
        return AssistantNo;
    }

    public void setAssistantNo(String assistantNo) {
        AssistantNo = assistantNo;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }
}
