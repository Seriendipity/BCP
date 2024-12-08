package com.example.bcp.entity;

import java.io.Serializable;
import java.util.Objects;

public class MentionedId implements Serializable {

    private String commentId;
    private String mentionedUser;

    // Getters and Setters
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getMentionedUser() {
        return mentionedUser;
    }

    public void setMentionedUser(String mentionedUser) {
        this.mentionedUser = mentionedUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MentionedId)) return false;
        MentionedId that = (MentionedId) o;
        return Objects.equals(commentId, that.commentId) &&
                Objects.equals(mentionedUser, that.mentionedUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, mentionedUser);
    }
}
