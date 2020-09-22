package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement userName;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "btnSubmit")
    private WebElement btnSubmit;

    private WebDriverWait webDriverWait;
    private JavascriptExecutor jse;

    public LoginPage(WebDriver driver) {

        PageFactory.initElements(driver, this);
        webDriverWait = new WebDriverWait(driver,60);
        jse = (JavascriptExecutor)driver;
    }

    public void setUser(String userName_, String password_){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(userName)).click();
        userName.sendKeys(userName_);
        password.sendKeys(password_);
    }

    public void sumbit(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        this.jse.executeScript("arguments[0].click()", btnSubmit);
    }

}
