package com.itsv.annotation.util;

import java.util.Random;

public class UTILCN {
	
	public static final String ALLCHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";  
	
	//Éú³ÉËæ»úÂë
	public static String sj(){
		StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < 16; i++) {  
            sb.append(UTILCN.ALLCHAR.charAt(random.nextInt(UTILCN.ALLCHAR.length())));  
        }
        return sb.toString();
	} 
}
