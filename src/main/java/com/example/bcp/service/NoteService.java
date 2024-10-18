package com.example.bcp.service;

import com.example.bcp.entity.Note;
import com.example.bcp.mapper.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteMapper noteMapper;

    public List<Note> selectAllNote(){
        return noteMapper.selectAllNote();
    }

    public List<Note> selectByStudentNo(String StudentNo){
        return noteMapper.selectByStudentNo(StudentNo);
    }

    public void insertNote(String NoteNo, String StudentNo, String NoteInformation,
                           String NotePath , boolean Authority){
        noteMapper.insertNote(NoteNo,StudentNo,NoteInformation,NotePath , Authority);
    }

    public void deleteNote(String NoteNo){
        noteMapper.deleteNote(NoteNo);
    }

    public void updateNoteAuthority(String NoteNo , boolean Authority){
        noteMapper.updateNoteAuthority(NoteNo,Authority);
    }

    public void updateNoteInformation(String NoteInformation , String NoteNo){
        noteMapper.updateNoteInformation(NoteInformation,NoteNo);
    }
    public int maxNo(){
        return noteMapper.maxNo();
    }
}
