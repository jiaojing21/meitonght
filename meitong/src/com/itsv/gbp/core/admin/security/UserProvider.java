package com.itsv.gbp.core.admin.security;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

import com.itsv.gbp.core.admin.bo.UserService;
import com.itsv.gbp.core.admin.vo.SessionUser;
import com.itsv.gbp.core.admin.vo.User;

/**
 * 供安全模块使用的用户信息提供类
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-8 下午03:30:05
 * @version 1.0
 */
public class UserProvider implements UserDetailsService {

	private UserService userService;

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException,
			DataAccessException {
		User user = this.userService.queryByName(userName);
		if (null == user) {
			throw new UsernameNotFoundException(userName + " is not found.");
		}
		if (!user.getEnabled()) {
			throw new UsernameNotFoundException(userName + " is invalidation.");
		}
		
		SessionUser sessionUser = this.userService.loadUserWithMenu(user.getId());

		this.userService.getUnit(sessionUser);
		
		return new UserInfoAdapter(sessionUser);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
