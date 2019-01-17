package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class calcPage {
	



	public void AllOtherPage(WebDriver driver) {
	    PageFactory.initElements(driver, this);
	  }
	
	@FindBy(id="uscodreading")
	  public static WebElement CurrentOdometer;
	
	@FindBy(id="uscodreading")
	  public static WebElement previousOdometer;
	
	@FindBy(id="usgasputin")
	  public static WebElement gas;
	@FindBy(id="usgasprice")
	public static WebElement price;
	
	@FindBy(xpath="//*[@id=\\\"standard\\\"]/tbody/tr[5]/td/input")
	  public static WebElement calculate;

	@FindBy(xpath="//*[@id=\\\"content\\\"]/font/b")
	  public static WebElement result;

}
