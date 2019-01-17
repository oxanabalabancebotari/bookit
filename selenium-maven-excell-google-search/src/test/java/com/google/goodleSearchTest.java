package com.google;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class goodleSearchTest {
	WebDriver driver=null;
	Xls_Reader data= new Xls_Reader("C:\\Users\\balab\\Desktop\\google.xlsx");
	
	@BeforeTest
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver= new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
		
		
	}
	@AfterTest
	public void closeUp() {
		driver.quit();
	}
	
	
	@Test
	public void searchResult() {
		
		int rcount= data.getRowCount("data");
		for(int i=2; i<=rcount; i++) {
			String item= data.getCellData("data", "Item", i);
		
		
		driver.findElement(By.name("q")).sendKeys(item);
		driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
		 String result= driver.findElement(By.id("resultStats")).getText();
		 result= result.replace("About ","");
		 result= result.replace(" results", "");
		 data.setCellData("data", "SearchResult", i, result);
		 driver.navigate().back();
		 
	}
	}
}
