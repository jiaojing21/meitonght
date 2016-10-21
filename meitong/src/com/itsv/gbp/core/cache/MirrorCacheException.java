package com.itsv.gbp.core.cache;

import com.itsv.gbp.core.AppException;

/**
 * ������ģ��ĸ��쳣��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-11 ����11:04:54
 * @version 1.0
 */
public class MirrorCacheException extends AppException {

	private static final long serialVersionUID = -2495128558354635333L;

	public MirrorCacheException(String message) {
		super(message);
	}

	public MirrorCacheException(String message, Throwable cause) {
		super(message, cause);
	}
}
