package com.itsv.annotation.util;

import java.io.InputStream;
import java.util.Properties;


public class RePublic {
	/**
	 * 图片获取路径
	 */
	public static String URL = "";
	/**
	 * 图片写入路径
	 */
	public static String PICURL = "";
	
	static{
		{
			String resources = "/uploadurl.properties";
			InputStream in;
			try {
				in = RePublic. class .getResourceAsStream( resources );
				System.out.println(in);
				Properties p = new Properties();
				p.load(in);
				URL = p.get("readaddress").toString();
				PICURL = p.get("writeaddress").toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		{
			/**
			 * 第二个静态块
			 */
		}
	}
}
