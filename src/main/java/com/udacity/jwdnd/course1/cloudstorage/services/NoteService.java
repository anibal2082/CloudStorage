package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @Transactional
    public boolean saveNote(NoteForm noteForm, Integer userId){

        Note note = new Note();
        note.setNoteTitle(noteForm.getNoteTitle());
        note.setNoteDescription(noteForm.getNoteDescription());
        note.setUserId(userId);

        try {
            if (noteForm.getNoteId() == null) {
                noteMapper.insert(note);
                return true;
            }

            Note noteSaved = noteMapper.getNote(noteForm.getNoteId());

            if (noteSaved == null || noteSaved.getUserId() != userId)
                return false;

            note.setNoteId(noteForm.getNoteId());
            noteMapper.update(note);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    @Transactional
    public boolean deleteNote(Integer noteId, Integer userId){

        Note noteSaved = noteMapper.getNote(noteId);
        if (noteSaved == null || noteSaved.getUserId() != userId)
            return false;
        try{
            noteMapper.deleteNote(noteId);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public List<Note> getNotes(Integer userId){
        return noteMapper.getNotes(userId);
    }
}
