package com.itsv.annotation.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Upload {
	public static void SaveFileFromInputStream(InputStream stream, String path,
			String filename){
		FileOutputStream fs = null;;
		try {
			File file =new File(path);    
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdir();    
			}
			
			fs = new FileOutputStream(path + "/" + filename);
			byte[] buffer = new byte[1024 * 1024];
			int bytesum = 0;
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(null!=fs){
					fs.close();
				}
				if(null!=stream){
					stream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
