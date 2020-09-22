package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    @Transactional
    public boolean saveCredential(CredentialForm credentialForm, Integer userId){

        Credential credential = new Credential();
        credential.setUserId(userId);
        credential.setUrl(credentialForm.getCredentialUrl());
        credential.setUserName(credentialForm.getCredentialUserName());

        try {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodeKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(credentialForm.getCredentialPassword(), encodeKey);

            credential.setKey(encodeKey);
            credential.setPassword(encryptedPassword);
            if (credentialForm.getCredentialId() == null || credentialForm.getCredentialId().equals("")){
                credentialMapper.insert(credential);
                return true;
            }
            credential.setCredentialId(Integer.parseInt(credentialForm.getCredentialId()));

            Credential credentialSaved = credentialMapper.getCredential(credential.getCredentialId());

            if(credentialSaved == null || credentialSaved.getUserId() != userId)
                return false;

            credentialMapper.update(credential);
            return true;

        }
        catch(Exception e){
            return false;
        }
    }

    public List<Credential> getCredentials(Integer userId){

        List<Credential> res = credentialMapper.getCredentials(userId);
        return res;
    }

    @Transactional
    public boolean deleteCredential(Integer credentialId, Integer userId){


        Credential credentialSaved = credentialMapper.getCredential(credentialId);

        if(credentialSaved == null || credentialSaved.getUserId() != userId)
            return false;
        try {
            credentialMapper.delete(credentialId);
        }
        catch(Exception e){
            return false;
        }
        return true;
    }
}
