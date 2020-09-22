package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CreateEditDeleteCredentialsTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private SignupPage signup;
    private LoginPage login;
    private HomePage home;
    private CredentialTab credentialTab;

    private final String firstName = "Anibal";
    private final String lastName = "Meneses";
    private final String userName = "user1";
    private final String password = "123";

    private final String credentiaUrl = "http://hotmail.com";
    private final String credentialUsername = "hotmail1";
    private final String credentialPassword = "12345";

    private final String credentiaUrlModified = "http://hotmail.org";
    private final String credentialUsernameModified = "hotmail2";
    private final String credentialPasswordModified = "54321";

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {

        this.driver = new ChromeDriver();
        signup = new SignupPage(driver);
        login = new LoginPage(driver);
        home = new HomePage(driver);
        credentialTab = new CredentialTab(driver);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void createCredential()  {
        signUp();
        login();
        home.openCredentialTab();
        credentialTab.clickAddCredential();
        credentialTab.setCredential(credentiaUrl, credentialUsername, credentialPassword);
        credentialTab.saveCredential();
        goToPage("/home");
        home.openCredentialTab();
        Assertions.assertTrue(credentialTab.isCredentialSaved(credentiaUrl, credentialUsername, credentialPassword));

    }

    @Test
    @Order(2)
    public void vieSavedCredential()  {
        login();
        home.openCredentialTab();
        Assertions.assertEquals(credentialPassword, credentialTab.getPasswordSaved(credentiaUrl, credentialUsername));
    }

    @Test
    @Order(3)
    public void editNote()  {
        login();
        home.openCredentialTab();
        credentialTab.setModifiedCredential(credentiaUrl, credentialUsername, credentiaUrlModified, credentialUsernameModified, credentialPasswordModified);
        credentialTab.saveCredential();

        goToPage("/home");
        home.openCredentialTab();
        Assertions.assertTrue(credentialTab.isCredentialSaved(credentiaUrlModified, credentialUsernameModified, credentialPasswordModified));
    }

    @Test
    @Order(4)
    public void viewSavedModifiedCredential()  {
        login();
        home.openCredentialTab();
        Assertions.assertEquals(credentialPasswordModified, credentialTab.getPasswordSaved(credentiaUrlModified, credentialUsernameModified));
    }

    @Test
    @Order(5)
    public void deleteNote()  {
        login();
        home.openCredentialTab();
        credentialTab.deleteCredential(credentiaUrlModified, credentialUsernameModified);
        goToPage("/home");
        home.openCredentialTab();
        Assertions.assertFalse(credentialTab.isCredentialSaved(credentiaUrlModified, credentialUsernameModified, credentialPasswordModified));

    }

    private void signUp() {
        goToPage("/signup");
        signup.setUser(firstName, lastName,userName, password);
        signup.submit();
    }

    private void login(){
        goToPage("/login");
        login.setUser(userName, password);
        login.sumbit();
    }

    private void goToPage(String path) {
        driver.get("http://localhost:" + this.port + path);
    }

}
