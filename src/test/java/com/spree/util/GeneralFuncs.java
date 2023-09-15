package com.spree.util;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.IllegalFormatException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GeneralFuncs {
	

	public static String oAuth_Token() {
		String acToken;
		RequestSpecification httpRequest;
		  System.out.println("oAuth_Token()");
			RestAssured.baseURI = "https://demo.spreecommerce.org";
			httpRequest = RestAssured.given();
			httpRequest.basePath("");
			  
			System.out.println("Posting to /spree_oauth/token");
			JSONObject requestParams = new JSONObject();
			requestParams.put("grant_type", "password");
			requestParams.put("username", "c@s.com");
			requestParams.put("password", "123456");
			httpRequest.header("Content-Type", "application/json"); 
			httpRequest.body(requestParams.toJSONString());
			Response response = httpRequest.post("/spree_oauth/token");
			response.prettyPrint();
			

		    RestResponseUtil testUtil = new RestResponseUtil ();
			  testUtil.setResponse(response);
			  testUtil.testStatus(200);
			acToken=testUtil.getJsonString("access_token");
			System.out.println("oAuth Token is =>  " + acToken);
			return acToken;
		 }
	
	public static JSONObject readFile(String filename) throws IOException, ParseException
	{
		
		//Create json object of JSONParser class to parse the JSON data
		  JSONParser jsonparser=new JSONParser();
		  //Create object for FileReader class, which help to load and read JSON file
		  FileReader reader = new FileReader(".\\JSON_File\\"+filename);
		  //Returning/assigning to Java Object
		  Object obj = jsonparser.parse(reader);
		  //Convert Java Object to JSON Object, JSONObject is typecast here
		  JSONObject prodjsonobj = (JSONObject)obj;
		return prodjsonobj;
		
	}
	
	}
