package com.itsv.gbp.core.security.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.concurrent.SessionRegistry;
import org.acegisecurity.ui.logout.LogoutHandler;

/**
 * 用户退出处理器。清除session
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-17 下午05:09:25
 * @version 1.0
 */
public class SessionLogoutHandler implements LogoutHandler {

	private SessionRegistry sessionRegistry;

	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			sessionRegistry.removeSessionInformation(session.getId());
			request.getSession().invalidate();
		}
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

}
