package com.itsv.gbp.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author Swk
 *
 */
public class ResponseUtils {
	public static <T> Map<String, Object> sendList(List<T> T) {  
	    Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("root", T);  
	    map.put("success", true);  
	    return map;  
	}
	public static <T> Map<String, Object> sendList(List<T> T,Object obj) {  
	    Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("root", T);  
	    map.put("obj", obj);  
	    return map;  
	}
	public static <T> Map<String, Object> sendMap(Map<String, Object> map){
		return map;
	}
}
