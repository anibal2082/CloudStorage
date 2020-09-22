package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UploadFileTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private SignupPage signup;
    private LoginPage login;
    private HomePage home;
    private FileTab fileTab;

    private final String FIRST_NAME = "Anibal";
    private final String LAST_NAME = "Meneses";
    private final String USER_NAME = "user1";
    private final String PASSWORD = "123";

    private final String FILE_NAME = "test.txt";

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
        fileTab = new FileTab(driver);

    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void uploadFile(){
        Path resourceDirectory = Paths.get("src","test","resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        signUp();
        login();
        goToPage("/home");
        fileTab.uploadFile(absolutePath+"/"+FILE_NAME);
        goToPage("/home");
        Assertions.assertTrue(fileTab.isFileSaved(FILE_NAME));
    }

    @Test
    @Order(2)
    public void deleteFile(){
        login();
        goToPage("/home");
        fileTab.deleteFile(FILE_NAME);
        goToPage("/home");
        Assertions.assertFalse(fileTab.isFileSaved(FILE_NAME));
    }


    private void signUp() {
        goToPage("/signup");
        signup.setUser(FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD);
        signup.submit();
    }

    private void login(){
        goToPage("/login");
        login.setUser(USER_NAME, PASSWORD);
        login.sumbit();
    }

    private void goToPage(String path) {
        driver.get("http://localhost:" + this.port + path);
    }
}
