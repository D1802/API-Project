package api.project;

import java.io.File;

import org.json.JSONObject;
import org.json.JSONTokener;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestCase_02 {
    
    @Test(description = "Verify that the search City API Returns the correct number of results",groups = {"API Tests"})

    public void testcase02(){

        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
        RestAssured.basePath = "/api/v1/cities";

        RequestSpecification http = RestAssured.given();
        http.queryParam("q","beng");
        Response response = http.get();

       
         // Validate status code is 200
         Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

            // Validate the result is an array of length 1
        JSONArray resultsArray = new JSONArray(response.getBody().asString());
        Assert.assertEquals(resultsArray.length(), 1, "Expected array length 1");

         // Validate the description contains "100+ Places"
         JSONObject city = resultsArray.getJSONObject(0);
         String description = city.getString("description");
         System.out.println(description);
         Assert.assertTrue(description.contains("100+ Places"), "Description does not contain '100+ Places'");
         //Validation Schema

        //  JsonPath jp = new JsonPath(response.getBody().toString());
        //  Assert.assertNotNull(jp.prettyPrint(),"JSON Schema  is not returned");

        File obj = new File("src/test/resources/schema.json");
        JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchema(obj);
        response.then().assertThat().body(validator);


                 // Print details for confirmation
        System.out.println("City: " + city.getString("city"));
        System.out.println("Description: " + description);
 

    }

}
