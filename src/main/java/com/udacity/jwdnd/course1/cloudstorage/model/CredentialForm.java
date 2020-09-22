package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {

    private String credentialId;
    private String credentialUrl;
    private String credentialUserName;
    private String credentialPassword;

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(String credentialUrl) {
        this.credentialUrl = credentialUrl;
    }

    public String getCredentialUserName() {
        return credentialUserName;
    }

    public void setCredentialUserName(String credentialUserName) {
        this.credentialUserName = credentialUserName;
    }

    public String getCredentialPassword() {
        return credentialPassword;
    }

    public void setCredentialPassword(String credentialPassword) {
        this.credentialPassword = credentialPassword;
    }
}
