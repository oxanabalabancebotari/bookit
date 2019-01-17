package com.bookit.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class APIDay2 {
	
	String baseUrl= "http://3.88.249.183:1000/ords/hr";
	@Test
	public void employeesWithParams() {
		
		/*Given Accept type is JSON
		 * When I send a Get request to url/employees
		 * Then status code is 200
		 * And response content should be json
		 * and 100 employees data should be in json response body 
		 */
		given().accept(ContentType.JSON).and().param("limit", 100)
		.when().get(baseUrl+"/employees/").then()
		.assertThat().statusCode(200).and()
		.contentType(ContentType.JSON).and()
		.body("items.employee_id", Matchers.hasSize(100));
	}
	
	
	/*Given Accept type is JSON
	 * When I send a Get request to url/employees/110
	 * Then status code is 200
	 * And response content should be json
	 * and first name must be John 
	 * and email must be JCHEN
	 */
	
	@Test(enabled=false)
	public void testWithPathParams() {
		given().accept(ContentType.JSON).and()
		.param("id", 110)
		.when().get(baseUrl+"/employees/{id}").then()
		.statusCode(200).and().assertThat().contentType(ContentType.JSON)
		.and().assertThat().body("first_name",Matchers.equalTo("John"),"email",Matchers.equalTo("JCHEN"));
	
		
	}
	

	/*Given Accept type is JSON
	 * When I send a Get request to url/employees/110
	 * Then status code is 200
	 * And response content should be json
	 * and first name must be John 
	 * and email must be JCHEN
	 */
	
	@Test
	public void testWithJsonPath() {
		  
		  Response response = given().accept(ContentType.JSON)
		      .and().pathParam("id",110).when().get(baseUrl+"/employees/{id}");
		  
		  //get json body and assign to JsonPath Object 
		  JsonPath json = response.jsonPath();
		  
		  System.out.println(json.getString("first_name"));
		  System.out.println(json.getString("job_id"));
		  System.out.println(json.getInt("employee_id"));
		  
		  String actualFirstname = json.getString("first_name");
		  String expectedFirstname = "John";
		  
		  assertEquals(actualFirstname, expectedFirstname);

		  
		  
		}
	
	
	/*Given Accept type is JSON
	 * When I send a Get request to url/employees/
	 * Then status code is 200
	 * And response content should be json
	 * all dat should be returned
	 */
	
	@Test
	public void testJsonPathWithList() {
		
		Map<String,Integer> paramMap = new HashMap<>();
		  paramMap.put("limit", 100);
		  
		  
		  Response response = given().accept(ContentType.JSON)
		      .and().params(paramMap).when().get(baseUrl+"/employees/");
		  
		  JsonPath json = response.jsonPath();
		  
		  //verify status code 
		  assertEquals(response.statusCode(), 200);
		  
		  //get all employee ids and assign to one list 
		  List<Integer> empIDs = json.getList("items.employee_id");
		  
		  //verify we got 100 employees as a response 
		  assertEquals(empIDs.size(), 100);
		  
		  //get all emails and assign one list  and print them 
		  List<String>  emails = json.getList("items.email");
		  System.out.println(emails);
		  
		  //get all emp id that are greater than 150
		  List<String>  empID = json.getList("items.findAll{it.employee_id>150}.employee_id");
		  System.out.println(empID);
		  
		  //get all employees lastname whose salary greater than 700
		  List<String>  salary = json.getList("items.findAll{it.salary>7000}.last_name");
		 // List<Integer>  salary2 = json.getList("items.findAll{it.salary>7000}.salary");
//		  for(int i; i<salary.size(); i++) {
//			  
//		  }
		  
		  System.out.println(salary.listIterator());
		  
	}
	 @Test
	 public void testwithJsonToMap() {
		  Response response = given().accept(ContentType.JSON)
			      .and().when().get(baseUrl+"/employees/140");
			  
			  //we convert JSON result to hasmap data structure
			    Map<String,Object> jsonmap = response.as(HashMap.class);
			    
			    //get firstname value from map 
			  System.out.println(jsonmap.get("first_name"));
			  //get salary value from map
			  System.out.println(jsonmap.get("salary"));
			  
			  String actualFirstName = (String) jsonmap.get("first_name");
			  
			  assertEquals(actualFirstName, "Joshua"); 
	 }
	 
	 @Test
	 public void convertJsonToListofMap() {
		  Response response = given().accept(ContentType.JSON)
			      .and().when().get(baseUrl+"/departments");
		  JsonPath json = response.jsonPath();
		  
		  List<Map> result = json.getList("items",Map.class);
		 // System.out.println(result.get(5).get("department_name"));
		  
		  String actualDepartmentName= (String)result.get(4).get("department_name");
		  String expectedDepartmentName= "Shipping";
		  
		  assertEquals(actualDepartmentName,expectedDepartmentName);
		  
		  //department id
		  Integer actualDepartmentId= (Integer)result.get(4).get("department_id");
		  Integer expectedDepartmentId= 50;
		  System.out.println(actualDepartmentId);
		  assertEquals(actualDepartmentId,expectedDepartmentId);
	 }
	 /*Given Content type is JSON
	  * And Limit is 10
	  * When I send the GET request to url/regions
	  * the status code must be 200
	  * The I should see following data
	  *     1 Europe
	  *     2 Americas
	  *     3 Asia
	  *     4 Middle East and Africa
	  */
	 @Test 
	 public void regionTaskV1() {
	   
	   given().accept(ContentType.JSON).and().params("limit",10)
	   .when().get(baseUrl+"/regions").then().statusCode(200).and()
	   .assertThat().body("items.region_name", Matchers.hasItems("Europe","Americas","Asia","Middle East and Africa"));      
	   
	 }
	 @Test
	 public void regionTaskV2() {
	   
	   Response response = given().accept(ContentType.JSON).and().params("limit",10)
	       .when().get(baseUrl+"/regions");
	   
	   JsonPath json = response.jsonPath();
	   
	   //status code check
	   assertEquals(response.statusCode(), 200);
	  
	   //region verify
	   assertEquals(json.getString("items[0].region_name"), "Europe");
	   assertEquals(json.getString("items[1].region_name"), "Americas");
	   assertEquals(json.getString("items[2].region_name"), "Asia");
	   assertEquals(json.getString("items[3].region_name"), "Middle East and Africa");  
	   
	 }
	 
	 @Test
	 public void regionTaskV3() {
	   Response response = given().accept(ContentType.JSON).and().params("limit",10)
	       .when().get(baseUrl+"/regions");
	   
	   JsonPath json = response.jsonPath();
	   
	   //status code check
	   assertEquals(response.statusCode(), 200);
	  
	   //JSON into list of maps
	   List<Map> result=json.getList("items",Map.class);
	   
	  assertEquals(result.get(0).get("region_name"), "Europe");
	  assertEquals(result.get(1).get("region_name"), "Americas");
	  assertEquals(result.get(2).get("region_name"), "Asia");
	  assertEquals(result.get(3).get("region_name"), "Middle East and Africa");    
	   
	 }
}
