package com.spree.noutils;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;

public class AddressData {
	@DataProvider(name = "address")
	public Object[][] getAddressData() {
		String[] addrkeys= {"firstname","lastname","company","address1","address2","city","phone","zipcode","state_name","country_iso"};
		
		String[][] addrVals = {
			{"Crystal","Wen","LTIMindtree","5000 Quorum Dr","401","Dallas","7135555555","75240","TX","US"},
			{"","Monter Diaz","LTIMindtree","5000 Quorum Dr","401","Dallas","7135555555","75240","TX","US"},
			{"Ana","","LTIMindtree","5000 Quorum Dr","401","Dallas","7135555555","75240","TX","US"},
			{"Minh","Nguyen","LTIMindtree","","401","Dallas","7135555555","75240","TX","US"},
			{"Andrew","Velasquez","LTIMindtree","5000 Quorum Dr","401","","7135555555","75240","TX","US"},
			{"Abhi","Dixit","LTIMindtree","5000 Quorum Dr","401","Dallas","7135555555","75240","TX",""},
			{"Catherine","Mitchell","SomethingCool","Somewhere St","401","Dallas","7135555555","75240","TX","US"},
			{"Hannah","Hsu","IV","Otherplace Dr","401","Dallas","7135555555","75240","TX","US"},
		};
		
		ArrayList<JSONObject> createAddrBodies = new ArrayList<JSONObject>();
		for (int i = 0; i < addrVals.length; i++) {
			JSONObject address = new JSONObject();
			for (int j = 0; j < addrVals[i].length; j++)
				address.put(addrkeys[j], addrVals[i][j]);
			JSONObject createAddrBody = new JSONObject();
			createAddrBody.put("address", address);
			createAddrBodies.add(createAddrBody);
		}
		((JSONObject)createAddrBodies.get(6).get("address")).put("label", "office");
		((JSONObject)createAddrBodies.get(7).get("address")).put("label", "office");
		
		return new Object[][] {
			{createAddrBodies.get(0), 200, ""},
			{createAddrBodies.get(1), 422, "First Name can't be blank"},
			{createAddrBodies.get(2), 422, "Last Name can't be blank"},
			{createAddrBodies.get(3), 422, "Address can't be blank"},
			{createAddrBodies.get(4), 422, "City can't be blank"},
			{createAddrBodies.get(5), 500, "Internal Server Error"},
			{createAddrBodies.get(6), 200, ""},
			{createAddrBodies.get(7), 422, "Address name has already been taken"}

		};
	}
}
