package com.gascalculator.testcases;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.gascalculator.pages.GasCalculatorPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DDFTest {
  
	WebDriver driver ;
  WebDriverWait wait =null;
  Xls_Reader xl = new Xls_Reader("C:\\Users\\balab\\Selenium\\testing-maven\\gascalculator\\src\\test\\resources\\calctestdata.xlsx");
  GasCalculatorPage calculataPage;
  
  
  @BeforeTest
  public void setup() {
    
    WebDriverManager.chromedriver().setup();
    driver=new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    driver.manage().window().maximize();
    
  }
  
  @AfterTest
  public void closeup() {
    driver.quit();
  }
  
  @Test
  public void calculateTest() {
	  
	  
	  calculataPage= new GasCalculatorPage(driver);
	  driver.get("https://www.calculator.net/gas-mileage-calculator.html");
    
//    double currentOr=3456;
//    double previousOr=2345;
//    double gas=30;
    
    int rowcount=xl.getRowCount("Data");
    
    for(int i=2;i<=rowcount;i++) {
      
      
      String run = xl.getCellData("Data", "Execute", i);
      if(!run.equalsIgnoreCase("Y")) {
        xl.setCellData("Data", "Status", i, "Skip Requested");
        continue;
      }
      
      
      
      String currentOr = xl.getCellData("Data", "CurrentOR", i);
      String previousOr = xl.getCellData("Data", "PreviousOR", i);
      String gas = xl.getCellData("Data", "Gas", i);

      
      
      calculataPage.currentOdometer.clear();
      calculataPage.currentOdometer.sendKeys(currentOr);
      calculataPage.previousOdometer.clear();
      calculataPage.previousOdometer.sendKeys(previousOr);
      calculataPage.gas.clear();
      calculataPage.gas.sendKeys(gas);
      calculataPage.calculate.click();
    
      String[] actualResult = calculataPage.result.getText().split(" ");
      xl.setCellData("Data", "Actual", i, actualResult[0]);
          
      System.out.println(actualResult[0]);
      
      double co = Double.parseDouble(currentOr);
      double po = Double.parseDouble(previousOr);
      double gs = Double.parseDouble(gas);
    
      double expectedResult = (co-po)/gs;
      DecimalFormat df = new DecimalFormat("0.00");
      String expectedResult2=String.valueOf(df.format(expectedResult));
      xl.setCellData("Data", "Expected", i, expectedResult2);
    
      if(actualResult[0].equals(expectedResult2)) {
        xl.setCellData("Data", "Status", i, "PASS");
      }else {
        xl.setCellData("Data", "Status", i, "FAIL");
      }
      
      xl.setCellData("Data", "Time", i, LocalDateTime.now().toString());
      
    }
    
    
    
    
    
    
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

}
