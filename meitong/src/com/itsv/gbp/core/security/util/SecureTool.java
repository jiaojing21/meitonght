package com.itsv.gbp.core.security.util;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.WebAuthenticationDetails;
import org.acegisecurity.userdetails.UserDetails;

import com.itsv.gbp.core.security.SecureException;

/**
 * 安全工具类。<br>
 * 提供了便利的方法，供业务逻辑层直接访问到当前登录的用户信息，远程IP地址，sessionID等
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-1 下午06:11:34
 * @version 1.0
 */
public class SecureTool {

	/**
	 * 获取当前的安全对象。 <br>
	 * 该对象包含用户的详细信息及其他附加信息。 一般不直接使用，而是使用下面的。
	 * 
	 * @return
	 */
	public static Authentication getCurrentSecurityObject() throws SecureException {
		SecurityContext context = SecurityContextHolder.getContext();
		if (null == context || null == context.getAuthentication()) {
			throw new SecureException("用户认证对象不存在");
		}

		return context.getAuthentication();

	}

	/**
	 * 获得用户的详细信息。 <br>
	 * 为了通用性，返回的是UserDetails对象，可以强制类型转换为我们自己构造的UserInfo对象
	 * 
	 * @return
	 */
	public static UserDetails getCurrentUser() throws SecureException {
		Object user = getCurrentSecurityObject().getPrincipal();
		if (null == user || !(user instanceof UserDetails)) {
			throw new SecureException("用户信息不存在或类型不正确。getPrincipal:" + user);
		} else {
			return (UserDetails) user;
		}
	}

	/**
	 * 获得用户远程访问的IP地址
	 * 
	 * @return
	 */
	public static String getRemoteAddress() throws SecureException {
		Object other = getCurrentSecurityObject().getDetails();
		//这儿只考虑了HTTP请求情况，没考虑RMI情况
		if (null == other || !(other instanceof WebAuthenticationDetails)) {
			throw new SecureException("用户信息不存在或类型不正确。detail:" + other);
		} else {
			return ((WebAuthenticationDetails) other).getRemoteAddress();
		}
	}

	/**
	 * 获得用户在server端存放的sessionId。这个应该用不到。
	 * 
	 * @return
	 */
	public static String getSessionId() {
		Object other = getCurrentSecurityObject().getDetails();
		//这儿只考虑了HTTP请求情况，没考虑RMI情况
		if (null == other || !(other instanceof WebAuthenticationDetails)) {
			throw new IllegalStateException("用户信息不存在或类型不正确。detail:" + other);
		} else {
			return ((WebAuthenticationDetails) other).getSessionId();
		}
	}
}
