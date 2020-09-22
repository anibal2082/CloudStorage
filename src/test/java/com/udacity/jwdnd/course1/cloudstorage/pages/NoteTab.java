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

public class NoteTab {

    @FindBy(id = "addNote")
    private WebElement addNote;

    @FindBy(id = "noteId")
    private WebElement noteId;

    @FindBy(id = "noteTitle")
    private WebElement noteTitle;

    @FindBy(id = "noteDescription")
    private WebElement noteDescription;

    @FindBy(id = "saveNote")
    private WebElement saveNote;

    @FindBy(className="note-element")
    private List<WebElement> noteElements;

    private WebDriverWait webDriverWait;
    private JavascriptExecutor jse;


    public NoteTab(WebDriver driver) {

        PageFactory.initElements(driver, this);
        webDriverWait = new WebDriverWait(driver,60);
        jse = (JavascriptExecutor)driver;
    }

    public void clickAddNote(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addNote));
        this.jse.executeScript("arguments[0].click()", addNote);
    }

    public void setNote(String noteTitle_, String noteDescription_){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(noteTitle));
        noteTitle.sendKeys(noteTitle_);
        noteDescription.sendKeys(noteDescription_);
    }

    public void setModifiedNote(String noteTitle_, String noteDescription_, String newTitle, String newDescription){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addNote));
        List<String> result = new ArrayList<>();
        for(WebElement element: noteElements){
            WebElement titleElement = element.findElement(By.className("note-title"));
            WebElement descriptionElement = element.findElement(By.className("note-description"));
            WebElement editElement = element.findElement(By.className("note-edit")).findElement(By.id("btn-edit"));

            if(noteTitle_.equals(titleElement.getText()) && noteDescription_.equals(descriptionElement.getText())){
                editElement.click();
                webDriverWait.until(ExpectedConditions.elementToBeClickable(noteTitle));
                noteTitle.clear();
                noteDescription.clear();
                noteTitle.sendKeys(newTitle);
                noteDescription.sendKeys(newDescription);
            }

        }
    }

    public void deleteNote(String noteTitle_, String noteDescription_){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addNote));
        List<String> result = new ArrayList<>();
        for(WebElement element: noteElements){
            WebElement titleElement = element.findElement(By.className("note-title"));
            WebElement descriptionElement = element.findElement(By.className("note-description"));
            WebElement linkElement = element.findElement(By.className("note-edit")).findElement(By.id("link-delete"));

            if(noteTitle_.equals(titleElement.getText()) && noteDescription_.equals(descriptionElement.getText())){
                linkElement.click();
            }

        }
    }

    public void saveNote(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(saveNote));
        this.jse.executeScript("arguments[0].click()", saveNote);
    }

    public boolean isNoteSaved(String title, String description){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addNote));
        List<String> result = new ArrayList<>();
        for(WebElement element: noteElements){
            WebElement titleElement = element.findElement(By.className("note-title"));
            WebElement descriptionElement = element.findElement(By.className("note-description"));

            if(title.equals(titleElement.getText()) && description.equals(descriptionElement.getText()))
                return true;
        }
        return false;
    }

}
