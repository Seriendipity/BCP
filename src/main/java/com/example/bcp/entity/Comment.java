package com.example.bcp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @Column(name = "CommentId" , length = 8)
    private String CommentId;

    @Column(name = "DiscussionId" , length = 10)
    private String DiscussionId;

    @Column(name = "CommentInformation" , columnDefinition = "text")
    private String CommentInformation;

    @Column(name = "LikesNumber")
    private int LikesNumber;

    @Column(name = "CommentPostingTime")
    private LocalDateTime CommentPostingTime;

    public String getCommentId() {
        return CommentId;
    }

    public void setCommentId(String commentId) {
        CommentId = commentId;
    }

    public String getDiscussionId() {
        return DiscussionId;
    }

    public void setDiscussionId(String discussionId) {
        DiscussionId = discussionId;
    }

    public String getCommentInformation() {
        return CommentInformation;
    }

    public void setCommentInformation(String commentInformation) {
        CommentInformation = commentInformation;
    }

    public int getLikesNumber() {
        return LikesNumber;
    }

    public void setLikesNumber(int likesNumber) {
        LikesNumber = likesNumber;
    }

    public LocalDateTime getCommentPostingTime() {
        return CommentPostingTime;
    }

    public void setCommentPostingTime(LocalDateTime commentPostingTime) {
        CommentPostingTime = commentPostingTime;
    }
}