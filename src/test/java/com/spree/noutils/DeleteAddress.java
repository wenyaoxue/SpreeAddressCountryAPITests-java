package com.spree.noutils;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.spree.noutils.GetAddresses;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
public class DeleteAddress {
	Base base;
	GetAddresses getAddrs;;
	
	@BeforeTest
	public void init() {
		base = new Base();
		getAddrs = new GetAddresses();
	}
	
	public void deleteAll() {
		init();
		Map<Integer, Map<String, String>> addresses = getAddrs.getAll();
		base.setup();
		for (int id: addresses.keySet()) {
			deleteById(id);
		}
		addresses = getAddrs.getAll();
		assertEquals(0, addresses.size());
	}
	
	public void deleteById(int id) {
		System.out.println("Deleting from /api/v2/storefront/account/addresses/"+id);
		Response response = base.getReq()
				.auth()
				.oauth2(base.getAcToken())
				.contentType(ContentType.JSON)
				.delete("https://demo.spreecommerce.org/api/v2/storefront/account/addresses/"+id)
				.then()
				.extract()
				.response();
		assertEquals(204, response.getStatusCode());	
	}
}
