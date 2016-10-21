package com.itsv.platform;

import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;

public class UuidGenerator {
	
	/**
	 * 生成32位UUID，返回类型为String（32）
	 * @return
	 */
	public static String getUUID(){
		
		UUIDGenerator UUIDgenerator = UUIDGenerator.getInstance();
    	UUID uuid = UUIDgenerator.generateRandomBasedUUID();
    	String result = uuid.toString().replaceAll("-", "");   	
 	  
  	    return result;
	}
}
