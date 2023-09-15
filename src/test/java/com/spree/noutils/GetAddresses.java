package com.spree.noutils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetAddresses {
	Base base;
	
	public Map<Integer, Map<String, String>> getAll() { //returns map int id -> Map<String, String> details
		base = new Base();
		base.setup();
		
		System.out.println("Getting from /api/v2/storefront/account/addresses");
		Response response = base.getReq()
				.auth()
				.oauth2(base.getAcToken())
				.get("https://demo.spreecommerce.org/api/v2/storefront/account/addresses")
				.then()
				.extract()
				.response();
		response.getBody().prettyPrint();
		JsonPath body = response.getBody().jsonPath();
		List<Object> addresses = body.get("data");
		
		Map<Integer, Map<String, String>> returnAddrs = new HashMap<Integer, Map<String, String>>();
		for (int i = 0; i < addresses.size(); i++) {
			Map<String, Object> addr = (Map<String, Object>) addresses.get(i);
			returnAddrs.put(Integer.parseInt((String)addr.get("id")), (Map<String, String>)addr.get("attributes"));
		}
		return returnAddrs;
	}
	
	public boolean matchAddress(int id, Map<String, String> attributes) {
		Map<Integer, Map<String, String>> addresses = getAll();
		if (addresses.get(id) == null) {
			System.out.println("id not found");
			return false;
		}
		for (String attr: attributes.keySet()) {
			if (attributes.get(attr) != null)
				if (addresses.get(id).get(attr) == null || !addresses.get(id).get(attr).equals(attributes.get(attr))) {
					System.out.println(attr + " not matched");
					return false;
				}
		}
		return true;
	}
}
