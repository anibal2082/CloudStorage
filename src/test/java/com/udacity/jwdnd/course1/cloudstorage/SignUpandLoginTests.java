package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SignUpandLoginTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private SignupPage signup;
	private LoginPage login;
	private HomePage home;

	private final String firstName = "Anibal";
	private final String lastName = "Meneses";
	private final String userName = "user1";
	private final String password = "123";

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
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void unauthorizedUser(){
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/result");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/file");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/note");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/credential");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(3)
	public void signUpandLogin() throws InterruptedException {
		goToPage("/signup");

		signup.setUser(firstName, lastName,userName, password);
		signup.submit();
		goToPage("/login");
		login.setUser(userName, password);
		login.sumbit();
		Assertions.assertEquals("Home", driver.getTitle());
		home.logout();
		Assertions.assertEquals("Login", driver.getTitle());
		goToPage("/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(4)
	public void loginAndLogout() throws InterruptedException {
		goToPage("/login");
		login.setUser(userName, password);
		login.sumbit();
		Assertions.assertEquals("Home", driver.getTitle());
		home.logout();
		Assertions.assertEquals("Login", driver.getTitle());
		goToPage("/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(5)
	public void alreadySignedUpUser() throws InterruptedException {
		goToPage("/signup");
		signup.setUser(firstName, lastName,userName, password);
		signup.submit();
		Assertions.assertTrue(signup.isErrorAlert());
	}


	private void goToPage(String path) {
		driver.get("http://localhost:" + this.port + path);
	}

}
