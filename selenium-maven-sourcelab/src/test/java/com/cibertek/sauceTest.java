package com.cibertek;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class sauceTest {
	
	public static final String USERNAME = "sharkoxana";
    public static final String ACCESS_KEY = "e069dcc6-f149-4dcf-9915-0f646bc46452";
    public static final String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";
	
	@Test
	public void login() throws MalformedURLException {
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("platform", "Windows 8");
		caps.setCapability("version", "65.0");
		
		RemoteWebDriver driver= new RemoteWebDriver(new URL(URL),caps);
		driver.get("https://www.cnn.com/");
		System.out.println(driver.getTitle());
		driver.quit();
	}

}
