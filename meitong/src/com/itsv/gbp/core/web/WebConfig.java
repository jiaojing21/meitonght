package com.itsv.gbp.core.web;

import com.itsv.gbp.core.security.util.SecureTool;

/**
 * ˵����ǰ�������ࡣ�������ó��ñ�������
 * 
 * @author admin 2005-1-12
 */
public class WebConfig {

	/**
	 * WEB���У����ݸ�ǰ�˽������ʾ������request�����е�����
	 */
	public static final String DATA_NAME = "data";

	/**
	 * WEB���У����ݸ�ǰ�˽����Ԫ������request�����е�����
	 */
	public static final String META_NAME = "meta";

	/**
	 * WEB���У����ݸ�ǰ�˽������ʾ��Ϣ��request�����е�����
	 */
	public static final String MESSAGE_NAME = "message";

	/**
	 * ����Ĵ�����Ϣ������Ϣ��
	 */
	public static final String MESSAGE_TRACE_NAME = "message_trace";

	/**
	 * session�д�ŵ��û���������
	 * @deprecated ���ð�ȫģ���ṩ���û���Ϣ
	 * @see SecureTool#getCurrentUser()
	 */
	public static final String USER_NAME = "session_user";

	/**
	 * �ͻ��������������Ʋ�����
	 */
	public static final String TOKEN_KEY = "token";

	/**
	 * session�д�ŵ�token����������
	 */
	public static final String TOKEN_IN_SESSION = "TOKEN_IN_SESSION";
}