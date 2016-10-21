package com.itsv.test;

public class SplitMenuCode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
/*		String mc ="840003001" ;
		
		System.out.println(mc.length()/3);
		int c= mc.length()/3;
		
		for(int i = 1;i<c;i++){
			System.out.println(">>"+mc.substring(0,(mc.length()-i*3)));
		}*/
		
		String s1 = "840003001";
		String s2 = "84000300";
		
		System.out.println(s1.length()%3);
		System.out.println(s2.length()%3);

	}

}
