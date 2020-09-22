package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstName;

    @FindBy(id = "inputLastName")
    private WebElement lastName;

    @FindBy(id = "inputUsername")
    private WebElement userName;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "btnSubmit")
    private WebElement submitButton;

    @FindBy(id = "login-link")
    private WebElement link;

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    private WebDriverWait webDriverWait;
    private JavascriptExecutor jse;

    public SignupPage(WebDriver driver) {

        PageFactory.initElements(driver, this);
        webDriverWait = new WebDriverWait(driver,60);
        jse = (JavascriptExecutor)driver;
    }

    public void setUser(String firstName_, String lastName_, String userName_, String password_){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(firstName)).click();
        firstName.sendKeys(firstName_);
        lastName.sendKeys(lastName_);
        userName.sendKeys(userName_);
        password.sendKeys(password_);
    }

    public boolean isErrorAlert(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(firstName)).click();
        return errorMsg.isDisplayed();
    }

    public void submit(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(submitButton));
        this.jse.executeScript("arguments[0].click()", submitButton);
    }

}
