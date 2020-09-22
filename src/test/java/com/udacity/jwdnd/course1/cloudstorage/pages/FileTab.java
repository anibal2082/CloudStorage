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

public class FileTab {

    @FindBy(id = "submitFile")
    private WebElement submittFile;

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(className="file-element")
    private List<WebElement> fileElements;

    private WebDriverWait webDriverWait;
    private JavascriptExecutor jse;


    public FileTab(WebDriver driver) {
        PageFactory.initElements(driver, this);
        webDriverWait = new WebDriverWait(driver,60);
        jse = (JavascriptExecutor)driver;
    }


    public void uploadFile(String filePath){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(fileUpload));
        fileUpload.sendKeys(filePath);
        this.jse.executeScript("arguments[0].click()", submittFile);
    }


    public void deleteFile(String fileName){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(submittFile));
        List<String> result = new ArrayList<>();
        for(WebElement element: fileElements){
            WebElement nameElement = element.findElement(By.className("file-name"));
            WebElement linkElement = element.findElement(By.className("file-edit")).findElement(By.id("link-delete-file"));

            if(fileName.equals(nameElement.getText())){
                this.jse.executeScript("arguments[0].click()", linkElement);
            }
        }
    }


    public boolean isFileSaved(String fileName){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(submittFile));
        List<String> result = new ArrayList<>();
        for(WebElement element: fileElements){
            WebElement nameElement = element.findElement(By.className("file-name"));
            WebElement linkElement = element.findElement(By.className("file-edit")).findElement(By.id("link-view-file"));

            if(fileName.equals(nameElement.getText()))
                return true;
        }
        return false;
    }

}
