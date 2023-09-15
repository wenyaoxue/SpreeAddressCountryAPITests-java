package com.spree.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.spree.util.RestRequestUtil;
import com.spree.util.RestResponseUtil;
import com.spree.testdata.Spreecom_TestData;
import com.spree.util.GeneralFuncs;


public class AddressTest extends BaseTest {	
	@Test(priority=1)
	public void T01_getDefaultCountries() throws IOException, ParseException {
		
		for (String id: 
			(new RestResponseUtil(RestRequestUtil.responseWithToken("get", token, "/account/addresses")))
				.getValsFromListMap_ByKey("data", "id")
			)
			RestRequestUtil.responseWithToken("delete", token, "/account/addresses/" + id);
		
		
		res = RestRequestUtil.responseWithTokenAndBody( "post",
				token, GeneralFuncs.readFile("address.json"), "/account/addresses");
		System.out.println("-------------------------");
		res.prettyPrint();
		testUtil.setResponse(res);
		testUtil.testStatus(200);	

		//given that address data is correct
		(new RestResponseUtil(RestRequestUtil.responseWithToken("get", token, "/account/addresses")))
			.matchMap_UsingListMapKeyToMap( 
				testUtil.getJsonString("data.id"), "data", "id", "attributes", testUtil.getJsonMap("data.attributes")
			);
	}


}