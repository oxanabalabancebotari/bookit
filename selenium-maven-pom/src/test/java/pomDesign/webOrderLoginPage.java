package pomDesign;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Assert.ThrowingRunnable;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.WebOrderLoginPage;

public class webOrderLoginPage {
	
	WebDriver driver;
	@BeforeTest
	public void setUp() {
		WebDriverManager.chromedriver().setup(); //set properties
		driver= new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
		driver.manage().window().maximize();
		

}
	
	@AfterTest
	public void closeUp() {
		driver.quit();
	}
//	@Ignore
//	@Test
//	public void navigate() {
//	
//		driver.findElement(By.name("ctl00$MainContent$username")).sendKeys("Tester");
//		driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("test");
//		driver.findElement(By.name("ctl00$MainContent$login_button")).click();
//	}
	
	@Test
	public void loginUsingPOM() {
		WebOrderLoginPage loginPage= new WebOrderLoginPage(driver); 
			loginPage.username.sendKeys("Tester");
			loginPage.password.sendKeys("test");
			loginPage.loginButton.click();
		
	}
	@Test
	public void ErrorMessagePOM() {
		WebOrderLoginPage loginPage= new WebOrderLoginPage(driver); 
		loginPage.username.sendKeys("Abc");
		loginPage.password.sendKeys("123");
		loginPage.loginButton.click();
		
		String errorMSG= loginPage.errorMessage.getText();
		Assert.assertEquals("Invalid Login or Password.", errorMSG);
		
	}
	
	
}