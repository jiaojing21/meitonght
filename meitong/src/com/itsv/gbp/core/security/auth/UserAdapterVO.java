package com.itsv.gbp.core.security.auth;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.UserDetails;

import com.itsv.gbp.core.security.vo.UserVO;

/**
 * 用户适配器类。将应用系统的用户和权限类转换为Acegi安全框架可以识别的接口形式。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-17 下午03:54:43
 * @version 1.0
 */
public class UserAdapterVO implements UserDetails {

	private static final long serialVersionUID = -6102910704549880113L;

	//真实的用户类
	private UserVO user;

	public UserAdapterVO() {
	}

	/**
	 * 用真实的用户对象作为构造函数参数
	 * 
	 * @param user
	 */
	public UserAdapterVO(UserVO user) {
		this.user = user;
	}

	/**
	 * 得到用户的权限信息。也就是角色名
	 */
	public GrantedAuthority[] getAuthorities() {
		GrantedAuthority roles[] = new GrantedAuthorityImpl[this.user.getRoles().size()];
		for (int i = 0; i < roles.length; i++) {
			roles[i] = new GrantedAuthorityImpl(this.user.getRoles().get(i).getRoleName());
		}
		return roles;
	}

	/**
	 * 获取用户密码
	 */
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
		return this.user.getEnabled().booleanValue();
	}

}
