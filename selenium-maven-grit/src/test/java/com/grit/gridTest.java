package com.grit;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class gridTest {
	
	@Test
	public void login() throws MalformedURLException {
		DesiredCapabilities caps= DesiredCapabilities.chrome();
		caps.setPlatform(Platform.ANY);
		RemoteWebDriver driver= new RemoteWebDriver(new URL("http://192.168.110.109:4444/wd/hub"),caps);
		driver.get("https://www.cnn.com/");

	}
	
}
