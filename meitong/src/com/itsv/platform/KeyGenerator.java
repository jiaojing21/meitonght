package com.itsv.platform;

import java.io.*;

public class KeyGenerator {
	private static KeyGenerator KeyGen = new KeyGenerator();
	private static String keyFilePath = "";
	
	static{
		String path = KeyGenerator.class.getClassLoader().getResource("").getPath();
		path = path.substring(1);
		path = path.replace("%20", " ");
		path = path.replace("/", "//");
		path = path.substring(0, path.length() - 3);
		path = path.substring(0, path.lastIndexOf("//") + 2);
		System.out.println("**************************************************");
		System.out.println("**  主键生成器使用存储路径");
		System.out.println("**  " + path);
		System.out.println("**************************************************");
		KeyGenerator.setKeyFilePath(path);
	}

	private KeyGenerator() {
	}

	public static KeyGenerator getInstance() {
		return KeyGen;
	}

	public synchronized int getNextKey(Class voClass) {
		int Key = 1;

		RandomAccessFile f = null;
		try {
			f = new RandomAccessFile(getKeyFilePath() + voClass.getName() + ".ID", "rw");
			String str = f.readLine();
			if (str == null || str == "")
				str = "0";
			Key = Integer.parseInt(str);
			Key += 1;
			String sKey = String.valueOf(Key);
			sKey = sKey.trim();
			f.seek(0);
			f.writeBytes(sKey);
			f.close();
		} catch (IOException e) {
		}
		return Key;
	}

	public static String getKeyFilePath() {
		return keyFilePath;
	}

	public static void setKeyFilePath(String keyFilePath) {
		KeyGenerator.keyFilePath = keyFilePath;
	}
}