package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private UserMapper userService;

    public CredentialController(CredentialService credentialService, EncryptionService encryptionService, UserMapper userService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    @PostMapping
    public String saveCredential(CredentialForm credentialForm, Authentication authentication, Model model, NoteForm noteForm){

        Integer userID = userService.getUser(authentication.getName()).getUserId();
        if(!credentialService.saveCredential(credentialForm, userID)){
            model.addAttribute("fileUploadError", "Unable to save credential");
            return "result";
        }
        model.addAttribute("fileUploadSuccess", "OK");
        return "result";
    }

    @GetMapping("/{credentialId}")
    public String deleteCredential(CredentialForm credentialForm, @PathVariable Integer credentialId,
                                   Authentication authentication, Model model, NoteForm noteForm){

        Integer userID = userService.getUser(authentication.getName()).getUserId();
        if(!credentialService.deleteCredential(credentialId, userID)){
            model.addAttribute("fileUploadError", "Unable to delete credential");
            return "result";
        }
        model.addAttribute("fileUploadSuccess", "OK");
        return "result";
    }
}
