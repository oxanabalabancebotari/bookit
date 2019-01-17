package com.gascalculator.pages;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import pages.calcPage;

public class gasMileageCalculator {
	
	WebDriver driver;
	Xls_Reader xl=C:\Users\balab\Selenium\testing-maven\calculator\src\test\resources\calctestdata.xlsx;
  CurrentOdometer currentOdometer;
	
	
	@BeforeTest
	public void setUp() {
		WebDriverManager.chromedriver().setup(); //set properties
		driver= new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.calculator.net/gas-mileage-calculator.html");
		driver.manage().window().maximize();
		

}
	
	@AfterTest
	public void closeUp() {
		driver.quit();
	}
	
	@Test
	public void mileageReading() {
		
//		double currentOr=3456;
//		double previousOr=2345;
//		double gas=30;
		
		int rowcount=xl.getRowCount("Data");
		for(int i=2; i<=rowcount; i++) {
			String currentOr= xl.getCellData("Data", "CurrentOR", i);
			String previousOr= xl.getCellData("Data", "PreviousOR", i);
			String gas= xl.getCellData("Data", "Gas",i);
//			String expected= xl.getCellData("Data", "Expected", i);
//			String actual= xl.getCellData("Data", "Actual", i);
//			String status= xl.getCellData("Data", "Status", i);
//			String time= xl.getCellData("Data", "Time", i);
		}
		
		calcPage.CurrentOdometer.clear();
		calcPage.CurrentOdometer.sendKeys(currentOr);
		calcPage.previousOdometer.clear();
		calcPage.previousOdometer.sendKeys(previousOr);
		calcPage.gas.clear();
		calcPage.gas.sendKeys(String.valueOf(gas));
		calcPage.price.clear();
		calcPage.price.sendKeys("5");
		calcPage.calculate.click();
		 String[] actualresult= calcPage.result.getText().split(" ");
		 System.out.println(actualresult[0]);
		 
		 double expectedResult=(currentOr-previousOr)/gas;
		 DecimalFormat df = new DecimalFormat("0.00");
		 String expectedResult2= String.valueOf((df.format(expectedResult)));
		 
		 if(actualresult[0].equals(expectedResult2)) {
			 System.out.println("Pass");
		 }else {
			 System.out.println("Fail");
		 }
		 
		 
		
	}
	

}
