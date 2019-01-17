package com.bookit.step_definition;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.bookit.pages.SelfPage;
import com.bookit.pages.SignInPage;
import com.bookit.pages.TeamPage;
import com.bookit.utilities.BrowserUtils;
import com.bookit.utilities.ConfigurationReader;
import com.bookit.utilities.DBUtils;
import com.bookit.utilities.Driver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MyInfoStepDefs {
	
	@Given("user logs in using {string} {string}")
	public void user_logs_in_using(String email, String password) {
	   Driver.getDriver().get(ConfigurationReader.getProperty("url"));
	   Driver.getDriver().manage().window().maximize();
	   SignInPage signInPage = new SignInPage();
	   signInPage.email.sendKeys(email);
	   signInPage.password.sendKeys(password);
	   signInPage.signInButton.click();
	}

	@When("user is on the my self page")
	public void user_is_on_the_my_self_page() {
	   SelfPage selfPage = new SelfPage();
	   selfPage.goToSelf();
	   BrowserUtils.waitFor(2);
	}

	@Then("user info should match with the database records for {string}")
	public void user_info_should_match_with_the_database_records_for(String email) {
	   //writing our query
		
		String query = "Select firstname, lastname, role\n" +
				"from users\n"+
				"where email='"+email+"'";
		
		//get value from database and assign to map
		
		Map<String,Object> result= DBUtils.getRowMap(query);
		
		//assigning database values to variables
		String expectedFirstName = (String) result.get("firstname");
		String expectedLastName= (String) result.get("lastname");
		String expectedRole = (String) result.get("role");
		
		String expectedFullName= expectedFirstName+" "+expectedLastName;
		
		System.out.println(expectedFirstName);
		System.out.println(expectedLastName);
		System.out.println(expectedRole);
		
		//getting value from UI and assignit to variables
		SelfPage selfPage = new SelfPage();
		String actualFullName= selfPage.name.getText();
		String actualRole= selfPage.role.getText();
		
		System.out.println(actualFullName);
		System.out.println(actualRole);
		
		//compare UI and Database
		
		assertEquals(actualFullName,expectedFullName);
		assertEquals(actualRole,expectedRole);
	}
	
	@When("user is on the my team page")
	public void user_is_on_the_my_team_page() {
	   SelfPage selfPage = new SelfPage();
	   selfPage.goToTeam();
	   BrowserUtils.waitFor(2);
	}

	@Then("user info should match with the database records {string}")
	public void user_info_should_match_with_the_database_records(String email) {
		//query
		
		String query= "Select firstname,role from users\n"+
				"where team_id= (Select team_id from users where email ='"+email+"')";
		
		//assign query result to list of maps
		List<Map<String,Object>> result = DBUtils.getQueryResultMap(query);
		
		//-----------------------------UI check
		//import teampage and create team page object to reach names and roles from UI
		TeamPage teamPage= new TeamPage();
		
		//empty list for actual name
		List<String> actualNames = new ArrayList<>();
		
		for(WebElement el: teamPage.teamMemberNames) {
			//get each webelement name and add to actualNames list
			actualNames.add(el.getText());
		}
		
		//empty list for actual roles
		List<String> actualRole = new ArrayList<>();
		
		for(WebElement el: teamPage.teamMemberRoles) {
			//get each webelement name and add to actualrole list
			actualRole.add(el.getText());
		}
		
		//before one by one comparison check the number of the result from DB and UI
		Assert.assertEquals(result.size(), actualNames.size());
		
		for(Map<String,Object> row : result) {
			String firstName= (String) row.get("firstname");
			Assert.assertTrue(actualNames.contains(firstName));
		}
		
		for(Map<String,Object> row : result) {
			String role= (String) row.get("role");
			Assert.assertTrue(actualRole.contains(role));
		}
	   
	}
	
	


}
