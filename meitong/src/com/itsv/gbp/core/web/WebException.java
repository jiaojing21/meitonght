package com.itsv.gbp.core.web;

import com.itsv.gbp.core.AppException;

/**
 * web 层异常基类
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 上午10:34:35
 * @version 1.0
 */
public class WebException extends AppException {

	private static final long serialVersionUID = 1371448515277653983L;

	public WebException(String message) {
		super(message);
	}

	public WebException(String message, Throwable cause) {
		super(message, cause);
	}
}
