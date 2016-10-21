package com.itsv.annotation.util;

import java.io.*;
import java.util.*;

import org.dom4j.*;
import org.dom4j.io.*;

public class ParseXML {
	public List<Map<String, String>> WL = new ArrayList<Map<String, String>>();

	public ParseXML() {
		try {
			InputStream in = ParseXML.class.getClassLoader()
					.getResourceAsStream("express_code.xml");
			System.out.println(in);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(in);
			Element root = doc.getRootElement();
			Element foo;
			Iterator i = root.elementIterator("bean");
			while (i.hasNext()) {
				Map<String, String> map = new HashMap<String, String>();
				foo = (Element) i.next();
				// System.out.print("物流编码:" + foo.elementText("code"));
				// System.out.println("物流名称:" + foo.elementText("name"));
				map.put("name", foo.elementText("name"));
				map.put("code", foo.elementText("code"));
				this.WL.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("---");
		ParseXML px = new ParseXML();
		List<Map<String,String>> list = px.WL;
		for(Map<String,String> map : list){
			System.out.println(map.get("code"));
		}
	}
}
