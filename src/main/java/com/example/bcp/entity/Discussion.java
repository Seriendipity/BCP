package com.example.bcp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "Discussion")
public class Discussion {
    @Id
    @Column(name = "DiscussionId" , length = 10 )
    private String DiscussionId;

    @Column(name = "StudentNo" , length = 8)
    private String StudentNo;

    @Column(name = "Cid" , length = 10)
    private String Cid;

    @Column(name = "DiscussionInformation" , columnDefinition = "text")
    private String DiscussionInformation;

    @Column(name = "DiscussionPostingTime")
    private LocalDateTime DiscussionPostingTime;

    @Column(name = "imgUrl" , length = 100)
    private String imageUrl ;

    @Column(name = "mentionedUser" , columnDefinition = "TEXT")
    private String mentionedUser;

    @Column(name = "topic" , length = 20)
    private String topic;

    @Column(name = "starTimes")
    private int starTimes;

    public int getStarTimes() {
        return starTimes;
    }

    public void setStarTimes(int starTimes) {
        this.starTimes = starTimes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMentionedUser() {
        return mentionedUser;
    }

    public void setMentionedUser(String mentionedUser) {
        this.mentionedUser = mentionedUser;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDiscussionId() {
        return DiscussionId;
    }

    public void setDiscussionId(String discussionId) {
        DiscussionId = discussionId;
    }

    public String getStudentNo() {
        return StudentNo;
    }

    public void setStudentNo(String studentNo) {
        StudentNo = studentNo;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getDiscussionInformation() {
        return DiscussionInformation;
    }

    public void setDiscussionInformation(String discussionInformation) {
        DiscussionInformation = discussionInformation;
    }

    public LocalDateTime getDiscussionPostingTime() {
        return DiscussionPostingTime;
    }

    public void setDiscussionPostingTime(LocalDateTime discussionPostingTime) {
        DiscussionPostingTime = discussionPostingTime;
    }
}
