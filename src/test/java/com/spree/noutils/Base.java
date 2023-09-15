package com.spree.noutils;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Base {
	private String acToken;
	RequestSpecification httpRequest;
	@Test
	public void setup() {
		RestAssured.baseURI = "https://demo.spreecommerce.org";
		httpRequest = RestAssured.given();
		  
		System.out.println("Posting to /spree_oauth/token");
		JSONObject requestParams = new JSONObject();
		requestParams.put("grant_type", "password");
		requestParams.put("username", "c@s.com");
		requestParams.put("password", "123456");
		httpRequest.header("Content-Type", "application/json"); 
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.post("/spree_oauth/token");
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 200);
		 
		JsonPath jsonPathEvaluator = response.getBody().jsonPath();
		acToken=jsonPathEvaluator.get("access_token").toString();
		System.out.println("oAuth Token is =>  " + acToken);
	}
	
	public RequestSpecification getReq() { return httpRequest; }
	public String getAcToken() { return acToken; }
	
}
