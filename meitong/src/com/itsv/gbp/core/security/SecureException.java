package com.itsv.gbp.core.security;

import com.itsv.gbp.core.AppException;

/**
 * ��ȫģ���쳣����
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-1 ����06:08:08
 * @version 1.0
 */
public class SecureException extends AppException {

	private static final long serialVersionUID = 7606369441509623803L;

	public SecureException(String message) {
		super(message);
	}

	public SecureException(String message, Throwable cause) {
		super(message, cause);
	}
}
