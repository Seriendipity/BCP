package com.example.bcp.entity;

import jakarta.persistence.*;

@Entity
@IdClass(MentionedId.class)
@Table(name = "mentioned")
public class Mentioned {
    @Id
    @Column(name = "CommentId",length = 8)
    private String CommentId;

    @Id
    @Column(name = "MentionedUser",length = 8)
    private String MentionedUser;

    @Column(name = "Status")
    private Boolean Status = false;

    public String getCommentId() {
        return CommentId;
    }

    public void setCommentId(String commentId) {
        CommentId = commentId;
    }

    public String getMentionedUser() {
        return MentionedUser;
    }

    public void setMentionedUser(String mentionedUser) {
        MentionedUser = mentionedUser;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
