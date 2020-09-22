package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NoteTab;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CreateEditDeletNotesTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private SignupPage signup;
    private LoginPage login;
    private HomePage home;
    private NoteTab noteTab;

    private final String firstName = "Anibal";
    private final String lastName = "Meneses";
    private final String userName = "user1";
    private final String password = "123";

    private final String noteTitle = "Note title 1";
    private final String noteDescription = "Note description 1";

    private final String noteTitleModified = "Note title 2";
    private final String noteDescriptionModified = "Note description 2";

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
        noteTab = new NoteTab(driver);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void createNote()  {
        signUp();
        login();
        home.openNoteTab();
        noteTab.clickAddNote();
        noteTab.setNote(noteTitle, noteDescription);
        noteTab.saveNote();
        goToPage("/home");
        home.openNoteTab();
        Assertions.assertTrue(noteTab.isNoteSaved(noteTitle, noteDescription));

    }

    @Test
    @Order(2)
    public void editNote()  {
        login();
        home.openNoteTab();
        noteTab.setModifiedNote(noteTitle, noteDescription, noteTitleModified, noteDescriptionModified);
        noteTab.saveNote();

        goToPage("/home");
        home.openNoteTab();
        Assertions.assertTrue(noteTab.isNoteSaved(noteTitleModified, noteDescriptionModified));
    }

    @Test
    @Order(3)
    public void deleteNote()  {
        login();
        home.openNoteTab();
        noteTab.deleteNote(noteTitleModified, noteDescriptionModified);
        goToPage("/home");
        home.openNoteTab();
        Assertions.assertFalse(noteTab.isNoteSaved(noteTitleModified, noteDescriptionModified));

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
