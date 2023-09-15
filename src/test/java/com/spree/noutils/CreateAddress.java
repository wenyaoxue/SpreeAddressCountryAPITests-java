package com.spree.noutils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateAddress extends AddressData {
	Base base;
	DeleteAddress delAddr;
	GetAddresses getAddrs;
	
	@BeforeTest
	public void init() {
		base = new Base();
		delAddr = new DeleteAddress();
		delAddr.deleteAll();
		getAddrs = new GetAddresses();
	}

	@Test(dataProvider="address")
	public void createAddress(JSONObject createAddrBody, int expStatus, String expErr) throws IOException, ParseException {
		base.setup();
				

		System.out.println("Posting to /api/v2/storefront/account/addresses");
		Response response = base.getReq()
				.auth()
				.oauth2(base.getAcToken())
				.contentType(ContentType.JSON)
				.body(createAddrBody)
				.post("https://demo.spreecommerce.org/api/v2/storefront/account/addresses")
				.then()
				.extract()
				.response();
		response.getBody().prettyPrint();
		assertEquals(expStatus, response.getStatusCode());	
 
		if (!expErr.equals("")) {
			assertEquals(expErr, response.getBody().jsonPath().get("error"));
		} else {
			assertTrue(getAddrs.matchAddress(
					Integer.parseInt((String) response.getBody().jsonPath().get("data.id")), 
					(Map<String, String>) response.getBody().jsonPath().get("data.attributes")));
		}

	}
}
