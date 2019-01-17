package cybertek;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AmazonTest {
	
	@Test
	public void navigatetoamazon() {
		WebDriverManager.chromedriver().setup(); //set properties
		WebDriver driver= new ChromeDriver();
		driver.get("https://www.amazon.com/");
		driver.quit();
	}

}
