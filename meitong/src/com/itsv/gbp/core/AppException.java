package com.itsv.gbp.core;

/**
 * ������ܸ��쳣�ࡣ<br>
 * �Ƽ�python�����쳣����root Exception�̳���RuntimeException�����ڷ����ӿ�����ʽ������Щ�쳣��<br>
 * �����������ºô���<br>
 * <ol>
 * <li>����checked exception���ӽӿڼ��ɿ����ᷢ����Щ�쳣</li>
 * <li>���÷���ǿ�ƴ����쳣�����ң��ӿڵ��쳣�����ı�Ҳ����Ӱ�쵽���÷�����</li>
 * <ol>
 * 
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-4 ����03:09:23
 * @version 1.0
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 2376627903957963613L;

	public AppException(String message) {
		super(message);
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}
}
