package com.itsv.platform.common.fileMgr.util;

import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class fileEncrypter {

	private static String FILEKEY = "AD67EA2F3BE6E5ADD368DFE03120B5DF92A8FD8FEC2F0746";

	/**
	 * ���ܺ��� ���룺 Ҫ���ܵ��ļ������루��0-F��ɣ���48���ַ�����ʾ3��8λ�����룩�磺
	 * AD67EA2F3BE6E5ADD368DFE03120B5DF92A8FD8FEC2F0746 ���У� AD67EA2F3BE6E5AD
	 * DES����һ D368DFE03120B5DF DES����� 92A8FD8FEC2F0746 DES������ �����
	 * ��������ļ����ܺ󣬱��浽ͬһ�ļ�����������".tdes"��չ�����ļ��С�
	 */
	public static boolean encrypt(File fileIn) {
		try {
			byte[] bytK1 = getKeyByStr(FILEKEY.substring(0, 16));
			byte[] bytK2 = getKeyByStr(FILEKEY.substring(16, 32));
			byte[] bytK3 = getKeyByStr(FILEKEY.substring(32, 48));

			FileInputStream fis = new FileInputStream(fileIn);
			byte[] bytIn = new byte[(int) fileIn.length()];
			for (int i = 0; i < fileIn.length(); i++) {
				bytIn[i] = (byte) fis.read();
			}
			fis.close();
			// ����
			byte[] bytOut = encryptByDES(encryptByDES(
					encryptByDES(bytIn, bytK1), bytK2), bytK3);
			String fileOut = fileIn.getPath() + ".tdes";
			FileOutputStream fos = new FileOutputStream(fileOut);
			for (int i = 0; i < bytOut.length; i++) {
				fos.write((int) bytOut[i]);
			}
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ���ܺ��� ���룺 Ҫ���ܵ��ļ������루��0-F��ɣ���48���ַ�����ʾ3��8λ�����룩�磺
	 * AD67EA2F3BE6E5ADD368DFE03120B5DF92A8FD8FEC2F0746 ���У� AD67EA2F3BE6E5AD
	 * DES����һ D368DFE03120B5DF DES����� 92A8FD8FEC2F0746 DES������ �����
	 * ��������ļ����ܺ󣬱��浽�û�ָ�����ļ��С�
	 */
	public static boolean decrypt(File fileIn, File fileOut) {
		try {
			byte[] bytK1 = getKeyByStr(FILEKEY.substring(0, 16));
			byte[] bytK2 = getKeyByStr(FILEKEY.substring(16, 32));
			byte[] bytK3 = getKeyByStr(FILEKEY.substring(32, 48));

			FileInputStream fis = new FileInputStream(fileIn);
			byte[] bytIn = new byte[(int) fileIn.length()];
			for (int i = 0; i < fileIn.length(); i++) {
				bytIn[i] = (byte) fis.read();
			}
			fis.close();
			// ����
			byte[] bytOut = decryptByDES(decryptByDES(
					decryptByDES(bytIn, bytK3), bytK2), bytK1);
			fileOut.createNewFile();
			FileOutputStream fos = new FileOutputStream(fileOut);
			for (int i = 0; i < bytOut.length; i++) {
				fos.write((int) bytOut[i]);
			}
			fos.close();
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * ��DES��������������ֽ� bytKey��Ϊ8�ֽڳ����Ǽ��ܵ�����
	 */
	private static byte[] encryptByDES(byte[] bytP, byte[] bytKey) throws Exception {
		DESKeySpec desKS = new DESKeySpec(bytKey);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(desKS);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(Cipher.ENCRYPT_MODE, sk);
		return cip.doFinal(bytP);
	}

	/**
	 * ��DES��������������ֽ� bytKey��Ϊ8�ֽڳ����ǽ��ܵ�����
	 */
	private static byte[] decryptByDES(byte[] bytE, byte[] bytKey) throws Exception {
		DESKeySpec desKS = new DESKeySpec(bytKey);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(desKS);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(Cipher.DECRYPT_MODE, sk);
		return cip.doFinal(bytE);
	}

	/**
	 * ����������ַ���ʽ�������ֽ�������ʽ�� �������ַ�����AD67EA2F3BE6E5AD
	 * �����ֽ����飺{173,103,234,47,59,230,229,173}
	 */
	private static byte[] getKeyByStr(String str) {
		byte[] bRet = new byte[str.length() / 2];
		for (int i = 0; i < str.length() / 2; i++) {
			Integer itg = new Integer(16 * getChrInt(str.charAt(2 * i))
					+ getChrInt(str.charAt(2 * i + 1)));
			bRet[i] = itg.byteValue();
		}
		return bRet;
	}

	/**
	 * ����һ��16�����ַ���10����ֵ ���룺0-F
	 */
	private static int getChrInt(char chr) {
		int iRet = 0;
		if (chr == "0".charAt(0))
			iRet = 0;
		if (chr == "1".charAt(0))
			iRet = 1;
		if (chr == "2".charAt(0))
			iRet = 2;
		if (chr == "3".charAt(0))
			iRet = 3;
		if (chr == "4".charAt(0))
			iRet = 4;
		if (chr == "5".charAt(0))
			iRet = 5;
		if (chr == "6".charAt(0))
			iRet = 6;
		if (chr == "7".charAt(0))
			iRet = 7;
		if (chr == "8".charAt(0))
			iRet = 8;
		if (chr == "9".charAt(0))
			iRet = 9;
		if (chr == "A".charAt(0))
			iRet = 10;
		if (chr == "B".charAt(0))
			iRet = 11;
		if (chr == "C".charAt(0))
			iRet = 12;
		if (chr == "D".charAt(0))
			iRet = 13;
		if (chr == "E".charAt(0))
			iRet = 14;
		if (chr == "F".charAt(0))
			iRet = 15;
		return iRet;
	}
}
