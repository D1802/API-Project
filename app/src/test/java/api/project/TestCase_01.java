package api.project;

import java.util.UUID;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestCase_01 {

     RequestSpecification http;
  

    @Test(description = "Verify that a new user can be registered and login using APIs of QTrip",groups = {"API Tests"})
    public void testcase01(){
          // Register a new user
          String username = "darpansarode@gmail.com";
          String Password = "abc@123";
          RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
          RestAssured.basePath = "/api/v1/register";
          UUID uuid = UUID.randomUUID();
         String uuidAsString = uuid.toString();

          JSONObject registrationBody = new JSONObject();
          registrationBody.put("email", username+uuidAsString);
          registrationBody.put("password", Password);
          registrationBody.put("confirmpassword", Password);
  
          RequestSpecification httpRequest = RestAssured.given();
          Response responseReg = httpRequest.contentType("application/json")
                                           .body(registrationBody.toString())
                                           .when()
                                           .post();
  
          // Log the response body for debugging
          System.out.println("Registration Response Body: " + responseReg.getBody().asString());
  
          // Validate registration response
          Assert.assertTrue(responseReg.body().jsonPath().getBoolean("success"), "Registration Failed");
          Assert.assertEquals(responseReg.getStatusCode(), 201);
  
          String token = responseReg.body().jsonPath().getString("data.token");
          String userID = responseReg.body().jsonPath().getString("data.id");
  
          // Login with the registered user
          RestAssured.basePath = "/api/v1/login";
          JSONObject loginBody = new JSONObject();
          loginBody.put("email", username+uuidAsString);
          loginBody.put("password", Password);
  
          RequestSpecification httpLoginRequest = RestAssured.given();
          Response responseLogin = httpLoginRequest.contentType("application/json")
                                                   .body(loginBody.toString())
                                                   .when()
                                                   .post();
  
          // Log the response body for debugging
          System.out.println("Login Response Body: " + responseLogin.getBody().asString());
  
          // Validate login response
          Assert.assertTrue(responseLogin.body().jsonPath().getBoolean("success"), "Login Failed");
          Assert.assertEquals(responseLogin.getStatusCode(), 201);
  
          // Verify token and user ID are returned
        //   String loginToken = responseLogin.body().jsonPath().getString("data.token");
        //   String loginUserID = responseLogin.body().jsonPath().getString("data.id");
  
        //   Assert.assertNotNull(loginToken, "Token is not returned");
        //   Assert.assertNotNull(loginUserID, "User ID is not returned");
  
        //   // Print token and user ID
        //   System.out.println("Token: " + loginToken);
        //   System.out.println("User ID: " + loginUserID);
    }

    
}
