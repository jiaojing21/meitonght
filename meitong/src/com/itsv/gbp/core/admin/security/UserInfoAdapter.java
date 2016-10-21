package com.itsv.gbp.core.admin.security;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.log4j.Logger;

import com.itsv.gbp.core.admin.vo.SessionUser;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.cache.source.ServiceDataProvider;

public class UserInfoAdapter implements UserDetails {

	private static final long serialVersionUID = -2972204383297356456L;
	private static final Logger logger = Logger.getLogger(UserInfoAdapter.class);
	private SessionUser user;

	public UserInfoAdapter() {
	}

	/**
	 * 用真实的用户对象作为构造函数参数
	 * 
	 * @param user
	 */
	public UserInfoAdapter(SessionUser user) {
		this.user = user;
	}

	//得到真正的用户对象
	public SessionUser getRealUser() {
		return this.user;
	}

	//用用户对应角色的ID号作为权限标识
	public GrantedAuthority[] getAuthorities() {
/*		GrantedAuthority roles[] = new GrantedAuthorityImpl[this.user.getRoles().size()];
		for (int i = 0; i < roles.length; i++) {
			roles[i] = new GrantedAuthorityImpl(this.user.getRoles().get(i).getId().toString());
		}*/
		
		GrantedAuthority roles[] = new GrantedAuthorityImpl[this.user.getGrouproles().size()];
		for (int i = 0; i < roles.length; i++) {
			roles[i] = new GrantedAuthorityImpl(this.user.getGrouproles().get(i).getId().toString());
		}
		logger.debug("==>用户权限表示为'"+roles.length+"'<==");
		return roles;
	}

	public String getPassword() {
		return this.user.getPassword();
	}

	public String getUsername() {
		return this.user.getUserName();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public User getUser() {
		return user;
	}

}
