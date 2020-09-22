package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Select("SELECT * FROM NOTES WHERE  userid=#{userId}")
    List<Note> getNotes(Integer userId) ;

    @Select("SELECT * FROM NOTES WHERE  noteid=#{noteId}")
    Note getNote(Integer noteId) ;

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid=#{noteId}")
    void update(Note note);

    @Delete("DELETE FROM NOTES WHERE  noteid=#{noteId}")
    void deleteNote(Integer noteId) ;
}
