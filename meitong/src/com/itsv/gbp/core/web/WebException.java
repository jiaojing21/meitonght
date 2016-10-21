package com.itsv.gbp.core.web;

import com.itsv.gbp.core.AppException;

/**
 * web ���쳣����
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����10:34:35
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
