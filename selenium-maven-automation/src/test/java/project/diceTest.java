package project;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.Assert;

public class diceTest {
	WebDriver driver=null;
		
	@BeforeTest
	public void openBrowser() {
		WebDriverManager.chromedriver().setup(); //set properties
		WebDriver driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.navigate().to("https://www.dice.com/");
		}
	@AfterTest
	public void closeBrowser() {
		driver.close();
		driver.quit();
		}
	@DataProvider
	public Object[][] KeysAndValues() {
		Object[][] data = new Object[3][2];
		data[0][0] = "Java developer";
		data[0][1] = "va";
		data[1][0] = "javascript developer";
		data[1][1] ="va";
		data[2][0] = "ruby developer";
		data[2][1] = "va";
		return data;
		}
	
	
	@Test(dataProvider="KeysAndValues")
	public void SearchBox(String job, String location) {
		driver.findElement(By.xpath("//input[@id=\"search-field-keyword\"]")).sendKeys(job);
		driver.findElement(By.xpath("//input[@id=\"search-field-location\"]")).clear();
		driver.findElement(By.xpath("//input[@id=\"search-field-location\"]")).sendKeys(location);
		driver.findElement(By.id("findTechJobs")).click();
		String result=driver.findElement(By.cssSelector("[id=\"posiCountId\"]")).getText();
		String []x=result.split(",");
		String arr1=x[0];
		String arr2=x[1];
		String conv=arr1.concat(arr2); 
		System.out.println(conv);
		driver.navigate().back();
		}
	@Test(priority=1)
	public void navigateToDice() {
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.navigate().to("https://www.dice.com/");
		String title=driver.getTitle();
		String actualTitle="Find Jobs in Tech | Dice.com";
		Assert.assertEquals(title, actualTitle);
		driver.quit();
		}
	}