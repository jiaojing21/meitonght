package com.itsv.test;

import com.itsv.gbp.core.util.MD5;

public class M {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
boolean b = true;
		
		MD5 md = new MD5();
		String s ="123456";
		System.out.println(md.getMD5ofStr(s).toLowerCase());
	}

}
