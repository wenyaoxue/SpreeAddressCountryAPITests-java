package com.spree.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import com.spree.util.RestRequestUtil;
import com.spree.util.RestResponseUtil;
import com.spree.testdata.Spreecom_TestData;


public class CountryTest extends BaseTest {

	@Test(priority=1)
    public void T01_getDefaultCountries() {
    	res = RestRequestUtil.getResponse("/countries/default");
		testUtil.setResponse(res);
		testUtil.testStatus(200);	
        //jp = RestAssuredFuncs.getJsonPath(res,"data.attributes.iso_name");
        //System.out.println(testUtil.getClients(jp));
        JsonPath jsonPathEvaluator = res.getBody().jsonPath();
		  String isoname=jsonPathEvaluator.get("data.attributes.iso_name").toString();
		  System.out.println("ISO Name is =>  " + isoname);
		  
    }
    
    @Test(priority=2,dataProvider="country_iso",dataProviderClass=Spreecom_TestData.class)
    public void T02_retrieveaCountry(String iso, String iso_name, String iso3) {
    	res = RestRequestUtil.getResponse("/countries/"+iso);
		testUtil.setResponse(res);
		testUtil.testStatus(200);
        testUtil.matchJsonString("data.attributes.iso_name", iso_name);
		  
    }
    
    @Test(priority=3)
    public void T03_listallCountries() {
    	res = RestRequestUtil.getResponse("/countries/");
		testUtil.setResponse(res);
		testUtil.testStatus(200);	
        //jp = RestAssuredFuncs.getJsonPath(res,"data.attributes.iso_name");
        //System.out.println(testUtil.getClients(jp));
        JsonPath jsonPathEvaluator = res.getBody().jsonPath();
		  String isoname=jsonPathEvaluator.get("data.attributes.iso_name").toString();
		  System.out.println("ISO Name is =>  " + isoname);
		  
    }

   /* @Test
    public void T02_GetAllValue_UsingJSONPath() {
        res = RestAssuredFuncs.getResponse("/read?id=5890");
        testUtil.checkStatusIs200(res);
        jp = RestAssuredFuncs.getJsonPath(res);
        System.out.println("Opt: " + jp.get("name"));
        System.out.println("Description: " + jp.get("description"));
        System.out.println("Type: " + jp.get("price"));
    }*/
}