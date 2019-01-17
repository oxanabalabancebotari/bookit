package com.bookit.tests;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.bookit.utilities.DBUtils;

public class JDBCtest {

//
//        String dbUrl = "jdbc:postgresql://localhost:5432/hr";
//        String dbUsername = "postgres";
//        String dbPassword = "abc";

 @Test(enabled=false)
 public void PostGreSQL() throws SQLException {
      Connection connection = DriverManager.getConnection("dbUrl", "dbUsername" , "dbPassword");
      Statement  statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      ResultSet resultset = statement.executeQuery("SELECT * FROM countries");
     
      //next method--> move pointer to next row
      //resultset.next();
      //System.out.println( resultset.getString(1) +"-"+ resultset.getString("country_name") +"-" +resultset.getInt(3));
      
      //will bring all the results
//      while(resultset.next()) {
//    	  System.out.println( resultset.getString(1) +"-"+ resultset.getString("country_name") +"-" +resultset.getInt(3)); 
//      }
      
      resultset.next();
      resultset.next();
      resultset.next();
     
      System.out.println(resultset.getRow());
      
      resultset.first();
      System.out.println(resultset.getRow());
      
      resultset.last();
      System.out.println(resultset.getRow());
      
      //how to move first row and loop everything again
      resultset.beforeFirst();
      while(resultset.next()) {
    	  System.out.println( resultset.getString(1) +"-"+ resultset.getString("country_name") +"-" +resultset.getInt(3)); 
    	  }
      
      
      resultset.close();
      statement.close();
      connection.close();
}
 
 
    @Test(enabled=false)
    public void JDBCMetaData()throws SQLException {
        Connection connection = DriverManager.getConnection("dbUrl", "dbUsername" , "dbPassword");
        Statement  statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultset = statement.executeQuery("SELECT * FROM employees");
        
        //database metadata
        DatabaseMetaData dbMetadata= connection. getMetaData();
        
        //which username are we using?
        System.out.println("User: "+ dbMetadata.getUserName());
        
        //database product name
        System.out.println("Data base Product name:"+ dbMetadata.getDatabaseProductName());
        
        // product version
        System.out.println("Database Product Version"+ dbMetadata.getDatabaseProductVersion());
        //---------------------
        
        //result set metadata create object
        ResultSetMetaData rsMetaData= resultset.getMetaData();
        
        //how many columns we have
        System.out.println("Colums count "+ rsMetaData.getColumnCount());
        
        //get collumn name
        System.out.println("Collumn name "+ rsMetaData.getColumnName(1));
        
        //get table name
        System.out.println("Table Name "+ rsMetaData.getTableName(1));
        
        // print all the collumn name using loop
        int columnCount = rsMetaData.getColumnCount();
        
        for(int i=1;i<=columnCount;i++) {
          System.out.println(rsMetaData.getColumnName(i));
        }
        
        resultset.close();
        statement.close();
        connection.close();
}
    
    @Test (enabled=false)
    public void DBUtil() throws SQLException {
      Connection connection = DriverManager.getConnection("dbUrl", "dbUsername", "dbPassword");
      Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);;
      ResultSet resultset = statement.executeQuery("select first_name,last_name,salary,job_id from employees");
      
      //database metadata(create object)
      DatabaseMetaData dbMetadata =connection.getMetaData();
      
      
      
      //resultset metadata create object
      ResultSetMetaData rsMetadata = resultset.getMetaData();
      
      List<Map<String,Object>> queryData = new ArrayList<>();
      
      //we will add the first row data in this map
      Map<String,Object> row1 = new HashMap<>();
      
      //point the first row
      resultset.next();
      
      //key is collumn name
      row1.put("first_name", resultset.getObject("first_name"));
      row1.put("last_name", "King");
      row1.put("salary", 24000);
      row1.put("Job_id", "AD_PRES");
      
      
      //verify if map keeps the data
      System.out.println(row1.toString());
      
      
      // push row 1 map to list
      queryData.add(row1);
      System.out.println(queryData.get(0).get("first_name"));
      
      //add one more row
      Map<String,Object> row2 = new HashMap<>();
      
      row2.put("first_name", resultset.getObject("first_name"));
      row2.put("last_name", resultset.getObject("last_name"));
      row2.put("salary", resultset.getObject("salary"));
      row2.put("Job_id", resultset.getObject("job_id"));
      
      queryData.add(row2);

      System.out.println("First Row: "+queryData.get(0).toString());
      System.out.println("Second Row: "+queryData.get(1).toString());
      
      resultset.close();
      statement.close();
      connection.close();
    }
    
    @Test (enabled=false)
    public void DBUtilDynamic() throws SQLException {
      Connection connection = DriverManager.getConnection("dbUrl", "dbUsername", "dbPassword");
      Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);;
      ResultSet resultset = statement.executeQuery("Select * From countries");
      
      //database metadata(create object)
      DatabaseMetaData dbMetadata =connection.getMetaData();
          
      //resultset metadata create object
      ResultSetMetaData rsMetadata = resultset.getMetaData();
      //----------DYNAMIC LIST FOR EVERY QUERY----------------
      
      //Main List
      List<Map<String,Object>> queryData = new ArrayList<>();
      
      //number of columns
      int colCount = rsMetadata.getColumnCount();
      
        while(resultset.next()) {
        //creating map to adding each row inside 
        Map<String,Object> row = new HashMap<>();
          
        
          for(int i=1;i<=colCount;i++) {
            
            row.put(rsMetadata.getColumnName(i), resultset.getObject(i));
          }
        
              
        //adding each row map to list   
        queryData.add(row);
        
        }
        
        //printing first 4 row
        System.out.println(queryData.get(0));
        System.out.println(queryData.get(1));
        System.out.println(queryData.get(2));
        System.out.println(queryData.get(3));
        
        
        //printing all the country name from the list
        for( Map<String,Object> map : queryData) {
        	System.out.println(map.get("country_name"));
        	
        }
        
      resultset.close();
      statement.close();
      connection.close();
    }
    
    @Test
    public void useDBUtils() {
    	DBUtils.createConnection();
    	
    	String query="select first_name,last_name,salary,job_id from employees";
    	
    	List<Map<String,Object>> queryData = DBUtils.getQueryResultMap(query);
    	
    	//first row salary value
    	System.out.println(queryData.get(0).get("salary"));
    	//close connection
    	DBUtils.destroy();
    }
    
    @Test
    public void useDBUtils2() {
      
      //create connection with given information 
      DBUtils.createConnection();
      
      String query = "SELECT first_name,last_name,salary,job_id FROM employees where employee_id=107";
      
      List<Map<String,Object>> queryData = DBUtils.getQueryResultMap(query);
        
      //print first row salary value 
      
      System.out.println(queryData.get(0).get("job_id"));
      
      //close connection 
      DBUtils.destroy();
      
    }
}