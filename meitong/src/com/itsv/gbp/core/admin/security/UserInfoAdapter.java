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
	 * ����ʵ���û�������Ϊ���캯������
	 * 
	 * @param user
	 */
	public UserInfoAdapter(SessionUser user) {
		this.user = user;
	}

	//�õ��������û�����
	public SessionUser getRealUser() {
		return this.user;
	}

	//���û���Ӧ��ɫ��ID����ΪȨ�ޱ�ʶ
	public GrantedAuthority[] getAuthorities() {
/*		GrantedAuthority roles[] = new GrantedAuthorityImpl[this.user.getRoles().size()];
		for (int i = 0; i < roles.length; i++) {
			roles[i] = new GrantedAuthorityImpl(this.user.getRoles().get(i).getId().toString());
		}*/
		
		GrantedAuthority roles[] = new GrantedAuthorityImpl[this.user.getGrouproles().size()];
		for (int i = 0; i < roles.length; i++) {
			roles[i] = new GrantedAuthorityImpl(this.user.getGrouproles().get(i).getId().toString());
		}
		logger.debug("==>�û�Ȩ�ޱ�ʾΪ'"+roles.length+"'<==");
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
