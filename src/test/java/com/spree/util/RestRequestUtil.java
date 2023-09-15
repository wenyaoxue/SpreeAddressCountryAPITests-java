package com.spree.util;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestRequestUtil {
    public static void setBaseURI() {
    	System.out.println("setBaseURI()");
    	RestAssured.baseURI = "https://demo.spreecommerce.org";
    }
    public static void setBasePath(String basePathTerm) {
    	System.out.println("setBasePath()");
        RestAssured.basePath = basePathTerm;
    }
    public static void resetBaseURI() {
    	System.out.println("resetBaseURI()");
        RestAssured.baseURI = null;
    }
    public static void resetBasePath() {
    	System.out.println("resetBasePath()");
        RestAssured.basePath = null;
    }
    public static void setContentType(ContentType Type) {
    	System.out.println("setContentType()");
        given().contentType(Type);
    }
    public static void setContentTypeJson() {
    	System.out.println("setContentTypeJson()");
    	given().header("Content-Type","application/JSON");
    }
    public static void setBody(JSONObject body) {
    	System.out.println("setBody()");
    	given().body(body.toJSONString());
    }
    private static RequestSpecification reqWithToken(RequestSpecification req, String token) {
    	System.out.println("reqWithToken()");
    	return req.auth().oauth2(token);
    }
    private static RequestSpecification reqWithJsonBody(RequestSpecification req, JSONObject body) {
    	System.out.println("reqWithJsonBody()");
    	return req.contentType(ContentType.JSON).body(body);
    }
    
    public static Response getResponse(String path) {
    	System.out.println("getResponse()");
        return given().get(path);
    }
    public static Response postResponse(String path) {
    	System.out.println("postResponse()");
        return given().post(path);
    }
    public static Response patchResponse(String path) {
    	System.out.println("patchResponse()");
        return given().patch(path);
    }
    public static Response deleteResponse(String path) {
    	System.out.println("deleteResponse()");
        return given().delete(path);
    }
    
    public static Response responseWithToken(String method, String token, String path) {
    	System.out.println("postResponseWithToken()");
        RequestSpecification tokenreq = reqWithToken(given(), token);
        switch (method) {
        	case "get": return tokenreq.get(path).then().extract().response();
        	case "post": return tokenreq.post(path).then().extract().response();
        	case "put": return tokenreq.put(path).then().extract().response();
        	case "delete": return tokenreq.delete(path).then().extract().response();
        	case "patch": return tokenreq.patch(path).then().extract().response();
        }
        System.out.println("no good method");
        return null;
    }

    public static Response responseWithTokenAndBody(String method, String token, JSONObject body, String path) {
    	System.out.println("postResponseWithTokenAndBody()");
        RequestSpecification tokenbody = reqWithJsonBody(  reqWithToken(given(), token),  body );
        switch (method) {
        	case "get": return tokenbody.get(path).then().extract().response();
        	case "post": return tokenbody.post(path).then().extract().response();
        	case "put": return tokenbody.put(path).then().extract().response();
        	case "delete": return tokenbody.delete(path).then().extract().response();
        	case "patch": return tokenbody.patch(path).then().extract().response();
        }
        System.out.println("no good method");
        return null;
    }

}



