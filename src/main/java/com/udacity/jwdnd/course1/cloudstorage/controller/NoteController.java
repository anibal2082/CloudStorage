package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @GetMapping("/{noteId}")
    public String deleteNote(Authentication authentication, @PathVariable Integer noteId,
                             Model model, NoteForm noteForm, CredentialForm credentialForm) {

        Integer userId = userService.getUser(authentication.getName()).getUserId();
        if(!noteService.deleteNote(noteId, userId)){
            model.addAttribute("fileUploadError", "Unable to delete note");
            return "result";
        }
        model.addAttribute("fileUploadSuccess", "OK");
        return "result";
    }

    @PostMapping
    public String saveNote(Authentication authentication, Model model, NoteForm noteForm, CredentialForm credentialForm) {

        Integer userID = userService.getUser(authentication.getName()).getUserId();
        if(!noteService.saveNote(noteForm, userID)){
            model.addAttribute("fileUploadError", "Unable to save note");
            return "result";
        }
        model.addAttribute("fileUploadSuccess", "OK");
        return "result";
    }

}
