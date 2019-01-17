package com.bookit.tests;




import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class APIDay1 {
	
	//when I sed a get request to URL employyees
	//when response status code should be 200
	String baseUrl= "http://3.88.249.183:1000/ords/hr";
	
	@Test
	public void simpleStatusCode() {
		when().get("http://3.88.249.183:1000/ords/hr/employees").then().statusCode(200);
		
	}
	
	//when I sent a Get request to URL countries
	//I should see Json format
	

	@Test
	public void printResponse() {
		when().get("http://3.88.249.183:1000/ords/hr/countries")
		.body().prettyPrint();
		
	}
	
	//when I sent a Get request to URL countries
	//and accept type of app json
	//I should see Json format
	
	@Test
	public void getWithHeaders() {
		given().accept(ContentType.JSON).
		when().get("http://3.88.249.183:1000/ords/hr/countries")
		.then().statusCode(200);
		
	}
	
	//negative test case
	
	@Test
	public void negativeGet() {
		when().get("http://3.88.249.183:1000/ords/hr/employees/1234").then().statusCode(404);
		
	}
	
	//when i send get request
	//then status code is 404
	//and response body error message should include not found
	
	@Test
	public void negativeGetWithBody() {
		 Response response = when().get("http://3.88.249.183:1000/ords/hr/employees/1234");
		 
		//status code assertion
		 
		 assertEquals(response.statusCode(),404);
		 
		 //check body includes "Not Found"
		 
		 assertTrue(response.asString().contains("Not Found"));
		
		//print the response
		 
		 response.prettyPrint();
		
	}
	
	//when I send a get request
	//and accept type is json
	//and status code is 200
	//and response content should be json
	
	@Test
	public void verifyContentType() {
		given().accept(ContentType.JSON)
        .when().get("http://3.88.249.183:1000/ords/hr/employees/100")
        .then().statusCode(200).and().contentType(ContentType.JSON);
	}
	
	//when I send a get request to url
	//then status code is 200
	//content type should be json
	//and first name should be "John"
	
	
	@Test
	public void verifyFirstName() {
		
		given().accept(ContentType.JSON).when().get(baseUrl+"/employees/110").
		then().statusCode(200).and().contentType(ContentType.JSON)
		.and().body("first_name",Matchers.equalTo("John"));
	}
	
	/* Given Accept type is JSON
	 * When I send a GET request to url/employees/150
	 * Then status code is 200
	 * And response content should be JSON
	 * and last name should be "Tucker"
	 * and JOB_ID should be "SA_REP"
	 */
	
	@Test
	public void firstNameAndJobId() {
		given().accept(ContentType.JSON).when().get(baseUrl+"/employees/150").then()
		.statusCode(200).and().contentType(ContentType.JSON).and().body("last_name", Matchers.equalTo("Tucker"))
		.and().body("job_id",Matchers.equalTo("SA_REP"));
	}
	
	/* Given Accept type is JSON
	 * When I send a GET request to url/employees/150
	 * Then status code is 200
	 * And response content should be JSON
	 * and 4 regions should be returned
	 */
	
	@Test
	public void verifyRegionCount() {
		given().accept(ContentType.JSON).when().get(baseUrl+"/regions")
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType(ContentType.JSON)
		.and().assertThat().body("items.region_id",Matchers.hasSize(4))
		.and().assertThat().body("items.region_name", Matchers.hasItem("Europe"))
		.and().assertThat().body("items.region_name", Matchers.hasItems("Europe","Americas"));
	}

}
