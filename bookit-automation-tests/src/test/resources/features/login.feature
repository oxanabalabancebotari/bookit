Feature: Login
 
   As a user, when I go to the login page
   I should be able to login 
   
   Background:
     Given the user is on the login page
     
  @login1 
Scenario: login as teacher
   When the user logs in as a teacher
   Then the user should logged in
   
   
  @login1 
  Scenario: login as a team 
     When the user logs in as a team leader
     Then the user should logged in
  
  
  Scenario: login as anyone 
     When the user logs using "kliversageu@cbslocal.com" and "kerrieliversage"
     Then the user should logged in
     

 
