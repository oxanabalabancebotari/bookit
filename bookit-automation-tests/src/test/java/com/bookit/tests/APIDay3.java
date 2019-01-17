package com.bookit.tests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import beans.Country;
import beans.CountryResponce;
import beans.Region;
import beans.RegionResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
public class APIDay3 {
	
	/*Given Accept Type is JSON
	 * And Content-Type is JSON
	 * When I sent  to POST request to URL/regions/
	 * with following JSON BODY
	 * {
	 *   "region_id":11,
	    "region_name":"myregion"
	 * 
	 * }
	 * 
	 * Then status code must be 201
	 * and response body should match request body 
	 */
	String baseUrl= "http://3.88.249.183:1000/ords/hr";
	@Test
	//not so good to use
	public void regionPOSTv1() {

	  String requestBody = "{\"region_id\":15,\"region_name\":\"myregion\"}";
	  
	  Response response = given().accept(ContentType.JSON)
	            .and().contentType(ContentType.JSON).and().body(requestBody)
	            .when().post(baseUrl+"/regions/");
	      
	  //verify status code for POST request
	  assertEquals(response.statusCode(), 201);
	  
	  JsonPath json =response.jsonPath();
	  //verify region name
	  assertEquals(json.getString("region_name"), "myregion");
	  //verify region id
	  assertEquals(json.getInt("region_id"), 15);

	}
	
	@Test
	public void regionPOSTv2() {
	  
	  //not a good way to pass JSON Request BODY
	  //String requestBody = "{\"region_id\":15,\"region_name\":\"myregion\"}";
	  
	  Map requestMap = new HashMap<>();
	  
	  requestMap.put("region_id", 124);
	  requestMap.put("region_name", "mytest");

	  //map will be converted to JSON 
	  Response response = given().accept(ContentType.JSON)
	            .and().contentType(ContentType.JSON).and().body(requestMap)
	            .when().post(baseUrl+"/regions/");
	      
	  //verify status code for POST request
	  assertEquals(response.statusCode(), 201);
	  
	  JsonPath json =response.jsonPath();
	  //verify region name
	  assertEquals(json.getString("region_name"), "mytest");
	  //verify region id
	  assertEquals(json.getInt("region_id"), 124);

	}
	
	@Test
	public void regionPOSTv4() {
	  
	  Region region = new Region();
	  
	  region.setRegion_id(156);
	  region.setRegion_name("mynewregion");
	  
	  
	  //map will be converted to JSON 
	  Response response = given().accept(ContentType.JSON)
	            .and().contentType(ContentType.JSON).and().body(region)
	            .when().post(baseUrl+"/regions/");
	      
	  //verify status code for POST request
	  assertEquals(response.statusCode(), 201);
	  
	  //create our POJO object for response body
	  
	  RegionResponse regionresponse = response.as(RegionResponse.class);
	  
	  assertEquals(regionresponse.getRegion_id(), region.getRegion_id());
	  assertEquals(regionresponse.getRegion_name(), region.getRegion_name());
	  
	    
	}
	
	/*Given Accept Type is JSON
	 * And Content-Type is JSON
	 * When I sent  to POST request to URL/countries/
	 * with following JSON BODY
	 * {
	 *  	"country_id": "AA",
    "country_name": "Cybertek",
    "region_id": 2,
	 * 
	 * }
	 * 
	 * Then status code must be 201
	 * and response body should match request body 
	 */
	@Test
	public void countryPOST() {
		
		Country country = new Country();
		country.setCountry_id("AA");
		country.setCountry_name("Cybertek");
		country.setRegion_id(2);
		
		
		Response responce = given().accept(ContentType.JSON)
				.and().contentType(ContentType.JSON).and().body(country)
	            .when().post(baseUrl+"/countries/");
		
		//start testing
		
		//status code
		assertEquals(responce.statusCode(), 201);
		
		//get json body from response and convert it to java objct
		
		CountryResponce countryresponce = responce.as(CountryResponce.class);
		
		//verify request body and responce body
		
		assertEquals(countryresponce.getCountry_id(), country.getCountry_id());
		assertEquals(countryresponce.getCountry_name(), country.getCountry_name());
		assertEquals(countryresponce.getRegion_id(), country.getRegion_id());
	}
	
	@Test
	public void countryPUT() {
		
		Country country = new Country();
		country.setCountry_id("AA");
		country.setCountry_name("CCCC");
		country.setRegion_id(2);
		
		
		Response responce = given().accept(ContentType.JSON)
				.and().contentType(ContentType.JSON).and().body(country)
	            .when().post(baseUrl+"/countries/"+country.getCountry_id());
		
		//start testing
		
		//status code
		assertEquals(responce.statusCode(), 200);
		
		//get json body from response and convert it to java objct
		
		CountryResponce countryresponce = responce.as(CountryResponce.class);
		
		//verify request body and responce body
		
		assertEquals(countryresponce.getCountry_id(), country.getCountry_id());
		assertEquals(countryresponce.getCountry_name(), country.getCountry_name());
		assertEquals(countryresponce.getRegion_id(), country.getRegion_id());
	}
	
	@Test
	public void countryDelete() {
		
		Response response = given().accept(ContentType.JSON).when().delete(baseUrl+"countries/AA");
		
		assertEquals(response.statusCode(),200);
		
		JsonPath json = response.jsonPath();
		
		assertEquals(json.getInt("rowsDeleted"),1);
	}

}
