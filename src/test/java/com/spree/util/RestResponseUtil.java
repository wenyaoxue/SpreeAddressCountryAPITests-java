package com.spree.util;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;

/*
 * usage
 * Get Token
			RestResponseUtil testUtil = new RestResponseUtil ();
			testUtil.setResponse(response);
			testUtil.testStatus(200);
			acToken=testUtil.getJsonString("access_token");
			System.out.println("oAuth Token is =>  " + acToken);
	Delete all Addresses
		for (String id: 
			(new RestResponseUtil(RestRequestUtil.responseWithToken("get", token, "/account/addresses")))
				.getValsFromListMap_ByKey("data", "id")
			)
			RestRequestUtil.responseWithToken("delete", token, "/account/addresses/" + id);
	Create Address (successful)
		testUtil.setResponse(res);
		testUtil.testStatus(200);	

		//given that address data is correct
		(new RestResponseUtil(RestRequestUtil.responseWithToken("get", token, "/account/addresses")))
			.matchMap_UsingListMapKeyToMap( 
				testUtil.getJsonString("data.id"), "data", "id", "attributes", testUtil.getJsonMap("data.attributes")
			);
 */

public class RestResponseUtil {
	Response res;
	public RestResponseUtil() {};
	public RestResponseUtil(Response res) {this.res = res;};
	public void setResponse(Response res) {this.res = res;}
	
	private  <T> String errMsg(String msg, T exp, T act) {
		return msg + " Expected " + exp + "; Actual " + act;
	}
	
	private int actStatus() {return res.getStatusCode();}
    public void testStatus(int expStatus) {
        assertEquals(actStatus(), expStatus, errMsg("Status test failed!", expStatus, actStatus()));
    }
    
    private JsonPath jsonPath() {return res.getBody().jsonPath();}
    
    public String getJsonString(String keyPath) {return jsonPath().get(keyPath);}
    public Map<String, String> getJsonMap(String keyPath) {return jsonPath().get(keyPath);}
    public List<Object> getJsonList(String keyPath) {return jsonPath().get(keyPath);}
    public List<Map<String, Object>> getJsonListOfMaps(String keyPath) {
    	System.out.println(keyPath);
    	res.prettyPrint();
    	return jsonPath().get(keyPath);
    	}
    public int getJsonInt(String keyPath) {return jsonPath().get(keyPath);}
    
    public void matchJsonString(String keyPath, String expVal) {
    	String actVal = getJsonString(keyPath);
        assertEquals(expVal, actVal, errMsg("JSON value [path "+keyPath+"] test failed!", expVal, actVal));
    }
    
    //Response[jsonKeyPath] = [  {  MAPKEYJSONKEY:unqid, MAPVALJSONKEY:othermap  }  ] 
    //eg Response['data']   = [  {  'id':123           , 'attributes':{'fn':'', 'ln':'',...}  }  ]
    //return: {  unqid: {'fn':'', 'ln':'',...}   }
//    public Map<String, Map<String, String>> getRemappedListMap
//    												(String jsonKeyPath, String mapKeyJSONKey, String mapValJSONKey ) {
//		  List<Map<String, Object>> listOfMaps = getJsonListOfMaps(jsonKeyPath);
//		  Map<String, Map<String, String>> returnMap = new HashMap<String, Map<String, String>>();
//		  for (int i = 0; i < listOfMaps.size(); i++) {
//			  Map<String, Object> map = listOfMaps.get(i);
//			  returnMap.put((String)map.get(mapKeyJSONKey), (Map<String, String>)map.get(mapValJSONKey));
//		  }
//		  return returnMap;
//    }
    
    //Response[jsonKeyPath] = [  {  MAPKEYJSONKEY:unqid, MAPVALJSONKEY:othermap  }  ] 
    //eg Response['data']   = [  {  'id':123           , 'attributes':{'fn':'', 'ln':'',...}  }  ]
    //return: [  unqid  ]
    public List<String> getValsFromListMap_ByKey
											(String listMapKeyPath, String mapKeyJSONKey) {
    	List<String> retList = new ArrayList<String>();
    	List<Map<String, Object>> listOfMaps = getJsonListOfMaps(listMapKeyPath);
    	for (int i = 0; i < listOfMaps.size(); i++) {
    		retList.add( (String) listOfMaps.get(i).get(mapKeyJSONKey));
    	}
    	return retList;
    }
        
    private boolean matchMaps(Map<String, String> map1, Map<String, String> map2) {
    	for (String key: map1.keySet()) {
			if (map1.get(key) != null)
				if (map2.get(key) == null || !map2.get(key).equals(map1.get(key))) {
					return false;
				}
		}
    	return true;
    }
    
    //Response[listMapKeyPath] = [  {  mayValKey:unqid, MAPVALJSONKEY:othermap  }  ] 
    //eg Response['data']      = [  {  'id':123       , 'attributes':{'fn':'', 'ln':'',...}  }  ]
    //return: for response['data'] element where 'id' = GIVENID, return whether 'attributes' = GIVENMAP
	public void matchMap_UsingListMapKeyToMap(String id, 
			String listMapKeyPath, String mapKeyKeyPath, String mayValKey, Map<String, String> mapExp) {
		
		  List<Map<String, Object>> listOfMaps = getJsonListOfMaps(listMapKeyPath);
		  for (int i = 0; i < listOfMaps.size(); i++) {
			  Map<String, Object> map = listOfMaps.get(i);
			  if (((String)(map.get(mapKeyKeyPath))).equals(id)) {
				  assertTrue( matchMaps(mapExp, (Map<String, String>)map.get(mayValKey)) , 
						  errMsg("Maps did not match!", mapExp.toString(), ((Map<String, String>)map.get(mayValKey)).toString()));
				  return;
			  }
		  }
		  assertTrue(false, "not found in response: " + listMapKeyPath + " element with key ["+mapKeyKeyPath+"] that has value "+mapExp.toString());
	}
    
}