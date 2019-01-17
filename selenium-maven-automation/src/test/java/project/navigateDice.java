package project;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class navigateDice {
	WebDriver driver=null;
	@Test
	public void navigatetoDice() {
		WebDriverManager.chromedriver().setup(); //set properties
		WebDriver driver= new ChromeDriver();
		driver.get("https://www.dice.com/");
		driver.manage().window().maximize();
		String title= driver.getTitle();
		String actualTitle="Find Jobs in Tech | Dice.com";
        assertTrue(driver.getTitle().contains("Find Jobs in Tech | Dice.com"));
       
	}
	@Test(dataProvider="data")
	public void test(String jobTitle,String zip) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"search-field-keyword\"]")).sendKeys(jobTitle);
		driver.findElement(By.xpath("//*[@id=\"search-field-location\"]")).sendKeys(zip);
		driver.findElement(By.id("findTechJobs")).click();
//		String rezult= driver.findElement(By.id("posiCountId")).getText();
//		Assert.assertTrue("Fail",1000>1000);
//		driver.findElement(By.cssSelector(".dice-navbar-brand>a")).click();
}
	
	@DataProvider
	public Object[][] data() {
		
		Object[][] data=new Object[3][2];
		
		data[0][0]="java developer";
		data[0][1]="va";
		data[1][0]="javascript developer";
		data[1][1]="va";
		data[2][0]="ruby developer";
		data[2][1]="va";
		
		return data;
		
}

}
