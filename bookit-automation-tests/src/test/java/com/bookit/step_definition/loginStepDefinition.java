package com.bookit.step_definition;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.Keys;

import com.bookit.pages.MapPage;
import com.bookit.pages.SignInPage;
import com.bookit.utilities.ConfigurationReader;
import com.bookit.utilities.Driver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class loginStepDefinition {
	
	@Given("the user is on the login page")
	public void the_user_is_on_the_login_page() {
	    //open browser and go to url
		String url= ConfigurationReader.getProperty("url");
		Driver.getDriver().get(url);
	}

	@When("the user logs in as a teacher")
	public void the_user_logs_in_as_a_teacher() {
		String email=ConfigurationReader.getProperty("teacher_email");
		String password= ConfigurationReader.getProperty("teacher_password");
		SignInPage signInPage= new SignInPage();
		signInPage.email.sendKeys(email);
		signInPage.password.sendKeys(password + Keys.ENTER);
	}

	@Then("the user should logged in")
	public void the_user_should_logged_in() {
		
		MapPage mapPage= new MapPage();
		Assert.assertTrue(mapPage.map.isDisplayed());
	}
	
	
	@When("the user logs in as a team leader")
	public void the_user_logs_in_as_a_team_leader() {
		String email=ConfigurationReader.getProperty("team_leader_email");
		String password= ConfigurationReader.getProperty("team_leader_password");
		SignInPage signInPage= new SignInPage();
		signInPage.email.sendKeys(email);
		signInPage.password.sendKeys(password + Keys.ENTER);
	}
	
	@When("the user logs using {string} and {string}")
	public void the_user_logs_using_and(String string, String string2) {
		SignInPage signInPage= new SignInPage();
		signInPage.email.sendKeys(string);
		signInPage.password.sendKeys(string2 + Keys.ENTER);
	}
	
	

}
