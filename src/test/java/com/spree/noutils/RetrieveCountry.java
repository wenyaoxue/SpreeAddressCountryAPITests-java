package com.spree.noutils;
import org.testng.Assert;
import org.testng.annotations.Test;

 

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

 

 

public class RetrieveCountry {

  @Test
  public void getCountry() {

      RestAssured.baseURI = "https://demo.spreecommerce.org";
      RequestSpecification httpRequest = RestAssured.given();
      Response response = httpRequest.request(Method.GET,"/api/v2/storefront/countries/ind");

      String responseBody = response.getBody().prettyPrint();
      System.out.println("Response Body is =>  " + responseBody);
      int statusCode = response.getStatusCode();
      System.out.println("Status code is =>  " + statusCode);
      Assert.assertEquals(200, statusCode);

      Assert.assertEquals(responseBody.contains("INDIA") /*Expected value*/, true /*Actual Value*/);


  }
}