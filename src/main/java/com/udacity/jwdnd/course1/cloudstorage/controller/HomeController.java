package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping()
public class HomeController {

        @Autowired
        private UserService userService;

        @Autowired
        private FileService fileService;

        @Autowired
        private NoteService noteService;

        @Autowired
        private CredentialService credentialService;

        @Autowired
        private EncryptionService encryptionService;

        @GetMapping("/home")
        public String loginView(Authentication authentication, NoteForm noteForm, CredentialForm credentialForm, Model model) {
            Integer userID = userService.getUser(authentication.getName()).getUserId();
            model.addAttribute("notes", noteService.getNotes(userID));
            model.addAttribute("files", fileService.getFiles(userID));
            model.addAttribute("credentials", credentialService.getCredentials(userID));
            model.addAttribute("encryptionService", encryptionService);
            return "home";
        }

    @GetMapping("/*")
    public String notFound(Model model){

        model.addAttribute("fileUploadError", "Not found");
        return "result";

    }


}
