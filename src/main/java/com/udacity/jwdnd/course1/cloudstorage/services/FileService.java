package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Transactional
    public boolean saveFile(MultipartFile mfile, Integer userId) {

        File file = new File();
        try {
            file.setContentType(mfile.getContentType());
            file.setFileData(mfile.getBytes());
            file.setFileName(mfile.getOriginalFilename());
            file.setUserId(userId);
            file.setFileSize(String.valueOf(mfile.getSize()));
            fileMapper.insert(file);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<File> getFiles(Integer userId) {

        return fileMapper.getFiles(userId);
    }

    public File getFile(Integer fileId) {

        return fileMapper.getFile(fileId);
    }

    public File getFileFromName(String filename, Integer userId) {

        return fileMapper.getFileFromName(filename, userId);
    }

    @Transactional
    public boolean deleteFile(Integer fileId, Integer userId) {
        try {
            File file = getFile(fileId);

            if(file == null || file.getUserId() != userId)
                return false;

            fileMapper.deleteFile(fileId);
        }
        catch(Exception e){
           return false;
        }
        return true;
    }
}
