package com.example.bcp.mapper;

import com.example.bcp.entity.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("Select * from Note")
    List<Note> selectAllNote();

    @Select("Select * from Note Where StudentNo = #{StudentNo}")
    List<Note> selectByStudentNo(String StudentNo);

    @Insert("Insert into Note(NoteNo,StudentNo,NoteInformation,NotePath,Authority)"+
    " values(#{NoteNo},#{StudentNo},#{NoteInformation},#{NotePath},#{Authority})")
    void insertNote(String NoteNo, String StudentNo, String NoteInformation,
                    String NotePath ,boolean Authority);

    @Delete("Delete from Note Where NoteNo = #{NoteNo}")
    void deleteNote(String NoteNo);

    @Update("Update Note Set Authority = #{Authority} where NoteNo = #{NoteNo}")
    void updateNoteAuthority(String NoteNo , boolean Authority);

    @Update("Update Note Set NoteInformation = #{NoteInformation} where NoteNo = #{NoteNo}")
    void updateNoteInformation(String NoteInformation , String NoteNo);
}
