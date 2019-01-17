package com.bookit.step_definition;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class scheduleStepDefinition {

   @Given("the user is on login page")
    public void the_user_is_on_login_page() {
    System.out.println("the user is on login");
}

	@When("go to my schedule")
	public void go_to_my_schedule() {
	   System.out.println("is on my schedule");
	}

	@Then("I should be able to see the reservation for my team")
	public void i_should_be_able_to_see_the_reservation_for_my_team() {
	   System.out.println("On reservation team");
	}

}
