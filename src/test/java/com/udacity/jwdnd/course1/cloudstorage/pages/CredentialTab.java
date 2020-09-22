package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class CredentialTab {

    @FindBy(id = "addCredential")
    private WebElement addCredential;

    @FindBy(id = "credentialUrl")
    private WebElement credentiallUrl;

    @FindBy(id = "credentialUsername")
    private WebElement credentialUsername;

    @FindBy(id = "credentialPassword")
    private WebElement credentialPassword;

    @FindBy(id = "saveCredential")
    private WebElement saveCredential;

    @FindBy(className="credential-element")
    private List<WebElement> credentialElements;

    private WebDriverWait webDriverWait;
    private JavascriptExecutor jse;


    public CredentialTab(WebDriver driver) {

        PageFactory.initElements(driver, this);
        webDriverWait = new WebDriverWait(driver,60);
        jse = (JavascriptExecutor)driver;
    }

    public void clickAddCredential(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addCredential));
        this.jse.executeScript("arguments[0].click()", addCredential);
    }

    public void setCredential(String credentiallUrl_, String credentialUsername_, String credentialPassword_){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(credentiallUrl));
        credentiallUrl.sendKeys(credentiallUrl_);
        credentialUsername.sendKeys(credentialUsername_);
        credentialPassword.sendKeys(credentialPassword_);
    }

    public void setModifiedCredential(String credentialUrl_, String credentiaUser_, String newUrl, String newUser, String newPassword){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addCredential));
        List<String> result = new ArrayList<>();
        for(WebElement element: credentialElements){
            WebElement urlElement = element.findElement(By.className("credential-url"));
            WebElement userElement = element.findElement(By.className("credential-username"));
            WebElement passwordElement = element.findElement(By.className("credential-password"));
            WebElement editElement = element.findElement(By.className("credential-edit")).findElement(By.id("btn-credential-edit"));

            if(credentialUrl_.equals(urlElement.getText()) && credentiaUser_.equals(userElement.getText())){
                editElement.click();
                webDriverWait.until(ExpectedConditions.elementToBeClickable(credentiallUrl));
                credentiallUrl.clear();
                credentialUsername.clear();
                credentialPassword.clear();
                credentiallUrl.sendKeys(newUrl);
                credentialUsername.sendKeys(newUser);
                credentialPassword.sendKeys(newPassword);
            }

        }
    }

    public String getPasswordSaved(String credentialUrl_, String credentiaUser_){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addCredential));
        List<String> result = new ArrayList<>();
        for(WebElement element: credentialElements){
            WebElement urlElement = element.findElement(By.className("credential-url"));
            WebElement userElement = element.findElement(By.className("credential-username"));
            WebElement passwordElement = element.findElement(By.className("credential-password"));
            WebElement editElement = element.findElement(By.className("credential-edit")).findElement(By.id("btn-credential-edit"));

            if(credentialUrl_.equals(urlElement.getText()) && credentiaUser_.equals(userElement.getText())){
                editElement.click();
                webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialPassword));

                String pass = credentialPassword.getAttribute("value");
                return pass;
            }

        }
        return "";
    }

    public void deleteCredential(String noteTitle_, String noteDescription_){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addCredential));
        List<String> result = new ArrayList<>();
        for(WebElement element: credentialElements){
            WebElement urlElement = element.findElement(By.className("credential-url"));
            WebElement userElement = element.findElement(By.className("credential-username"));
            WebElement passwordElement = element.findElement(By.className("credential-password"));
            WebElement linkElement = element.findElement(By.className("credential-edit")).findElement(By.id("link-credential-delete"));

            if(noteTitle_.equals(urlElement.getText()) && noteDescription_.equals(userElement.getText())){
                linkElement.click();
            }

        }
    }

    public void saveCredential(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(saveCredential));
        this.jse.executeScript("arguments[0].click()", saveCredential);
    }

    public boolean isCredentialSaved(String url, String user, String passowrd){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addCredential));
        List<String> result = new ArrayList<>();
        for(WebElement element: credentialElements){
            WebElement urlElement = element.findElement(By.className("credential-url"));
            WebElement userElement = element.findElement(By.className("credential-username"));
            WebElement passwordElement = element.findElement(By.className("credential-password"));

            if(url.equals(urlElement.getText()) && user.equals(userElement.getText())
                    && !passowrd.equals(passwordElement.getText()))
                return true;
        }
        return false;
    }

}
