package com.itsv.gbp.core.admin;

import com.itsv.gbp.core.admin.security.UserInfoAdapter;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.security.util.SecureTool;

/**
 * service基类。之类可以方便获取当前登录用户信息。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-20 下午12:25:43
 * @version 1.0
 */
public class BaseService {

	/**
	 * 从安全模块中获取当前登录的用户信息
	 * 
	 * @return
	 */
	protected User getCurrentUser() {
		UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
		return (adapter == null ? null : adapter.getRealUser());
	}

	/**
	 * 获取当前用户名
	 * @return
	 */
	protected String getUserId() {
		User user = getCurrentUser();
		return (user == null ? null : user.getId());
	}
}
