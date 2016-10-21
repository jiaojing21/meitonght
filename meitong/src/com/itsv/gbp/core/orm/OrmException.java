package com.itsv.gbp.core.orm;

import com.itsv.gbp.core.AppException;

/**
 * OR-map层的异常类
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-4 上午11:01:52
 * @version 1.0
 */
public class OrmException extends AppException {

	private static final long serialVersionUID = -9115422190134712141L;

	public OrmException(String message) {
		super(message);
	}

	public OrmException(String message, Throwable cause) {
		super(message, cause);
	}

}
