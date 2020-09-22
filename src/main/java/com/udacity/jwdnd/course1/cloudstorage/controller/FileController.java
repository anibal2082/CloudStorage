package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @GetMapping("/{fileId}")
    public String deleteFile(Authentication authentication, @PathVariable Integer fileId,
                             Model model, NoteForm noteForm, CredentialForm credentialForm) {

        Integer userId = userService.getUser(authentication.getName()).getUserId();

        if(!fileService.deleteFile(fileId, userId)){
            model.addAttribute("fileUploadError", "Unable to delete file");
            return "result";
        }

        model.addAttribute("fileUploadSuccess", "OK");
        return "result";
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<Resource> viewFile(Authentication authentication, @PathVariable Integer fileId,
                                             NoteForm noteForm, CredentialForm credentialForm) {

        File file = fileService.getFile(fileId);
        Integer userId = userService.getUser(authentication.getName()).getUserId();

        if(file == null || file.getUserId() != userId){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + file.getFileName() + "\"").body(new
                        ByteArrayResource(file.getFileData()));
    }

    @PostMapping
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file,
                             Model model, NoteForm noteForm, CredentialForm credentialForm) throws IOException {

        Integer userId = userService.getUser(authentication.getName()).getUserId();

        if(fileService.getFileFromName(file.getOriginalFilename(), userId) != null){
            model.addAttribute("fileUploadError", "Filename already exists");
            return "result";
        }

        if(!fileService.saveFile(file, userId)){
            model.addAttribute("fileUploadError", "Unable to save file");
            return "result";
        }
        model.addAttribute("fileUploadSuccess", "OK");
        return "result";
    }
}
