package api.project;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.text.ParseException;

public class TestCase_03 {


    String token;
        String userID;
        String outDate;

    @Test(description = "Verify that a reservation can be made using the QTrip API", groups = "API Tests")
    public void testcase03() throws ParseException {
        String username = "TC3_1@gmaill.com";   // Replace with your username
        String password = "abc@123";   // Replace with your password
        String city = "Bengaluru";       // Replace with your city name
        String date = "1998-16-11";     // Replace with your date
        String guestName = "darpan";     // Replace with guest name
        int count = 2;                  // Number of persons




        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net/";
        
        // Register user
        RestAssured.basePath = "api/v1/register";
        JSONObject obj = new JSONObject();
        obj.put("email", uuidAsString + username);
        obj.put("password", password);
        obj.put("confirmpassword", password);

        RequestSpecification http = RestAssured.given().log().all();
        Response resp = http.contentType("application/json").body(obj.toString()).when().post();
        System.out.println(resp.getStatusCode());
        System.out.println(resp.body().asPrettyString());
        Assert.assertTrue(resp.body().jsonPath().getBoolean("success"), "Registration Failed");

        // Log in
        RestAssured.basePath = "api/v1/login";
        JSONObject loginObj = new JSONObject();
        loginObj.put("email", uuidAsString + username);
        loginObj.put("password", password);
        
        RequestSpecification httpLog = RestAssured.given().log().all();
        Response respLog = httpLog.contentType("application/json").body(loginObj.toString()).when().post();
        
        token = respLog.body().jsonPath().getString("data.token");
        userID = respLog.body().jsonPath().getString("data.id");

        System.out.println("Token: " + token);
        System.out.println("UserID: " + userID);

        // Search for city
        RestAssured.basePath = "api/v1/cities";
        Response respCity = RestAssured.given().log().all().queryParam("q", city.toLowerCase().substring(0, 4)).get();
        List<HashMap<String, String>> list = respCity.getBody().jsonPath().getList("$");
        String id = list.get(0).get("id");
        System.out.println(id);

        // Convert date
        // SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        // SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-dd-MM");

        // try {
        //     Date Date = inputFormat.parse(date);
        //     String outputDate = outputFormat.format(Date);
        //     outDate = outputDate;
        //     System.out.println("Converted Date: " + outputDate);
        // } catch (ParseException e) {
        //     e.printStackTrace();
        // }

        // Make reservation
        RestAssured.basePath = "api/v1/reservations/new";
        JSONObject resvOBJ = new JSONObject();
        resvOBJ.put("userid", userID);
        resvOBJ.put("username", username);
        resvOBJ.put("date", date);
        resvOBJ.put("person", count);
        resvOBJ.put("adventureID", id);

        Response bookResp = RestAssured.given().log().all().header("Authorization", "Bearer " + token).contentType("application/json").body(resvOBJ.toString()).when().post();
        System.out.println(bookResp.getStatusCode());
        System.out.println(bookResp.body().asPrettyString());
        Assert.assertFalse(bookResp.body().jsonPath().getBoolean("success"), "Booking Not Successful");
    }
    
}
