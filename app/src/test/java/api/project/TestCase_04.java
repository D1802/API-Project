package api.project;

import java.util.UUID;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestCase_04 {

    @Test(description = "Verify that a duplicate user account cannot be created on the Qtrip Website",groups = {"API Tests"})
    public void testcase04(){
      String UserName ="TC4_1@gmail.com";
      String Password ="abc@123";

                  RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
                  RestAssured.basePath = "/api/v1/register";
                  UUID uuid = UUID.randomUUID();
                  String uuidAsString = uuid.toString();
        
                  JSONObject registrationBody = new JSONObject();
                  registrationBody.put("email",UserName+uuidAsString);
                  registrationBody.put("password", Password);
                  registrationBody.put("confirmpassword", Password);
          
                  RequestSpecification httpRequest = RestAssured.given();
                  Response responseReg = httpRequest.contentType("application/json")
                                                   .body(registrationBody.toString())
                                                   .when()
                                                   .post();
          
                  // Log the response body for debugging
                  System.out.println("Registration Response Body: " + responseReg.getBody().asString());

                  RestAssured.basePath = "/api/v1/register";
                  
                  JSONObject duplicateregistrationBody = new JSONObject();
                  duplicateregistrationBody.put("email",UserName+uuidAsString);
                  duplicateregistrationBody.put("password", Password);
                  duplicateregistrationBody.put("confirmpassword", Password);
          
                  RequestSpecification DuplicatehttpRequest = RestAssured.given();
                  Response DuplicateresponseReg = DuplicatehttpRequest.contentType("application/json")
                                                   .body(registrationBody.toString())
                                                   .when()
                                                   .post();
                  
                  System.out.println(DuplicateresponseReg.asPrettyString());
          
                  // Validate registration response
                  Assert.assertFalse(DuplicateresponseReg.body().jsonPath().getBoolean("success"), "Registration Failed");
                  Assert.assertEquals(DuplicateresponseReg.body().jsonPath().getString("message"),"Email already exists");
                  Assert.assertEquals(DuplicateresponseReg.getStatusCode(), 400);

                 
    }
       
}
