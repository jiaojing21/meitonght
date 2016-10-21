package com.itsv.gbp.core.security.util;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.acegisecurity.userdetails.UserDetails;

import com.itsv.gbp.core.security.SecureException;

/**
 * ��ȫ�����ࡣ<br>
 * �ṩ�˱����ķ�������ҵ���߼���ֱ�ӷ��ʵ���ǰ��¼���û���Ϣ��Զ��IP��ַ��sessionID��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-1 ����06:11:34
 * @version 1.0
 */
public class SecureTool {

	/**
	 * ��ȡ��ǰ�İ�ȫ���� <br>
	 * �ö�������û�����ϸ��Ϣ������������Ϣ�� һ�㲻ֱ��ʹ�ã�����ʹ������ġ�
	 * 
	 * @return
	 */
	public static Authentication getCurrentSecurityObject() throws SecureException {
		SecurityContext context = SecurityContextHolder.getContext();
		if (null == context || null == context.getAuthentication()) {
			throw new SecureException("�û���֤���󲻴���");
		}

		return context.getAuthentication();

	}

	/**
	 * ����û�����ϸ��Ϣ�� <br>
	 * Ϊ��ͨ���ԣ����ص���UserDetails���󣬿���ǿ������ת��Ϊ�����Լ������UserInfo����
	 * 
	 * @return
	 */
	public static UserDetails getCurrentUser() throws SecureException {
		Object user = getCurrentSecurityObject().getPrincipal();
		if (null == user || !(user instanceof UserDetails)) {
			throw new SecureException("�û���Ϣ�����ڻ����Ͳ���ȷ��getPrincipal:" + user);
		} else {
			return (UserDetails) user;
		}
	}

	/**
	 * ����û�Զ�̷��ʵ�IP��ַ
	 * 
	 * @return
	 */
	public static String getRemoteAddress() throws SecureException {
		Object other = getCurrentSecurityObject().getDetails();
		//���ֻ������HTTP���������û����RMI���
		if (null == other || !(other instanceof WebAuthenticationDetails)) {
			throw new SecureException("�û���Ϣ�����ڻ����Ͳ���ȷ��detail:" + other);
		} else {
			return ((WebAuthenticationDetails) other).getRemoteAddress();
		}
	}

	/**
	 * ����û���server�˴�ŵ�sessionId�����Ӧ���ò�����
	 * 
	 * @return
	 */
	public static String getSessionId() {
		Object other = getCurrentSecurityObject().getDetails();
		//���ֻ������HTTP���������û����RMI���
		if (null == other || !(other instanceof WebAuthenticationDetails)) {
			throw new IllegalStateException("�û���Ϣ�����ڻ����Ͳ���ȷ��detail:" + other);
		} else {
			return ((WebAuthenticationDetails) other).getSessionId();
		}
	}
}
