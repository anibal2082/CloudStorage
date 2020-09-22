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

public class HomePage {

    @FindBy(id ="nav-notes-tab")
    private WebElement tabNotes;

    @FindBy(id ="nav-credentials-tab")
    private WebElement tabCredential;


    @FindBy(id = "btnLogout")
    private WebElement btnLogout;

    private WebDriverWait webDriverWait;
    private JavascriptExecutor jse;


    public HomePage(WebDriver driver) {

        PageFactory.initElements(driver, this);
        webDriverWait = new WebDriverWait(driver,60);
        jse = (JavascriptExecutor)driver;
    }


    public void openNoteTab(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(tabNotes));
        this.jse.executeScript("arguments[0].click()", tabNotes);
    }

    public void openCredentialTab(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(tabCredential));
        this.jse.executeScript("arguments[0].click()", tabCredential);
    }

    public void logout(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(btnLogout));
        this.jse.executeScript("arguments[0].click()", btnLogout);
    }


}
