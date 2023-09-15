package com.spree.test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import com.spree.util.RestRequestUtil;
import com.spree.util.GeneralFuncs;
import com.spree.util.RestResponseUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

	public Response res = null; //Response
    public JsonPath jp  = null; //JsonPath
    public String token;

    //Instantiate a Helper Test Methods (testUtils) Object
    RestResponseUtil testUtil = new RestResponseUtil ();



    @BeforeClass
    public void setup() throws FileNotFoundException, IOException, ParseException {
    	token = GeneralFuncs.oAuth_Token();
        //Test Setup
    	RestRequestUtil.setBaseURI(); //Setup Base URI
    	RestRequestUtil.setBasePath("/api/v2/storefront"); //Setup Base Path
    	RestRequestUtil.setContentType(ContentType.JSON); //Setup Content Type
    }

    @AfterClass
    public void afterTest() {
        //Reset Values
    	RestRequestUtil.resetBaseURI();
    	RestRequestUtil.resetBasePath();
    }
}