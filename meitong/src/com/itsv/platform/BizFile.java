package com.itsv.platform;

import java.io.Serializable;

public class BizFile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5667309274353687798L;
	String originPathName;
	String originFileName;
	String postfix;
	String newFileName;

	public BizFile(String originPathName,String originFileName,String postfix,String newFileName){
		this.originFileName=originFileName;
		this.originPathName=originPathName;
		this.postfix=postfix;
		this.newFileName=newFileName;
	}
	
	public String getOriginPathName() {
		return originPathName;
	}

	public void setOriginPathName(String originPathName) {
		this.originPathName = originPathName;
	}

	public String getOriginFileName() {
		return originFileName;
	}

	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
}
