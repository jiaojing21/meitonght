package com.itsv.platform.common.fileMgr.util;

import java.io.*;
import java.util.*;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class zipFileUtil {
	
	public zipFileUtil() {
	}

	/**
	 * 压缩文件
	 * 
	 * @param srcfile
	 *            File 需要压缩的文件
	 * @param zipfile
	 *            File 压缩后的文件
	 */
	public static void ZipFiles(java.io.File srcfile, java.io.File zipfile) {
		byte[] buf = new byte[1024];
		try {
			// Create the ZIP file
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipfile));
			// Compress the files
			FileInputStream in = new FileInputStream(srcfile);
			// Add ZIP entry to output stream.
			out.putNextEntry(new ZipEntry(srcfile.getName()));
			// Transfer bytes from the file to the ZIP file
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			// Complete the entry
			in.close();
			// Complete the ZIP file
			out.closeEntry();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压缩
	 * 
	 * @param zipfile
	 *            File 需要解压缩的文件
	 * @param descfile
	 *            String 解压后的目文
	 */
	public static void UnZipFiles(java.io.File zipfile, String descfile) {
		try {
			// Open the ZIP file
			ZipFile zf = new ZipFile(zipfile);
			Enumeration entries = zf.entries();
			if (entries.hasMoreElements()){
				ZipEntry entry = ((ZipEntry) entries.nextElement());
				InputStream in = zf.getInputStream(entry);
				OutputStream out = new FileOutputStream(descfile);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				// Close the file and stream
				in.close();
				out.close();
			}
			zf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
