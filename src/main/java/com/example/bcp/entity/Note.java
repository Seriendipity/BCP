package com.example.bcp.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "Note")
@Schema(name = "Note", description = "$!{table.comment}")
public class Note {
    @Id
    @Column(name = "NoteNo" , length = 10)
    private String NoteNo;

    @Column(name = "StudentNo" , length = 10)
    private String StudentNo;

    @Column(name = "NoteInformation" , columnDefinition = "text")
    private String NoteInformation;

    @Column(name = "NotePath" , length = 100)
    private String NotePath;

    @Column(name = "Authority")
    private boolean Authority;

    @Column(name = "loadTime" )
    private LocalDateTime loadTime;

    public LocalDateTime getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(LocalDateTime loadTime) {
        this.loadTime = loadTime;
    }

    public boolean isAuthority() {
        return Authority;
    }

    public void setAuthority(boolean authority) {
        Authority = authority;
    }

    public String getNoteNo() {
        return NoteNo;
    }

    public void setNoteNo(String noteNo) {
        NoteNo = noteNo;
    }

    public String getStudentNo() {
        return StudentNo;
    }

    public void setStudentNo(String studentNo) {
        StudentNo = studentNo;
    }

    public String getNoteInformation() {
        return NoteInformation;
    }

    public void setNoteInformation(String noteInformation) {
        NoteInformation = noteInformation;
    }

    public String getNotePath() {
        return NotePath;
    }

    public void setNotePath(String notePath) {
        NotePath = notePath;
    }
}
