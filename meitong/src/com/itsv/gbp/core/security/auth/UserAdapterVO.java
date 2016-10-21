package com.itsv.gbp.core.security.auth;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.UserDetails;

import com.itsv.gbp.core.security.vo.UserVO;

/**
 * �û��������ࡣ��Ӧ��ϵͳ���û���Ȩ����ת��ΪAcegi��ȫ��ܿ���ʶ��Ľӿ���ʽ��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-17 ����03:54:43
 * @version 1.0
 */
public class UserAdapterVO implements UserDetails {

	private static final long serialVersionUID = -6102910704549880113L;

	//��ʵ���û���
	private UserVO user;

	public UserAdapterVO() {
	}

	/**
	 * ����ʵ���û�������Ϊ���캯������
	 * 
	 * @param user
	 */
	public UserAdapterVO(UserVO user) {
		this.user = user;
	}

	/**
	 * �õ��û���Ȩ����Ϣ��Ҳ���ǽ�ɫ��
	 */
	public GrantedAuthority[] getAuthorities() {
		GrantedAuthority roles[] = new GrantedAuthorityImpl[this.user.getRoles().size()];
		for (int i = 0; i < roles.length; i++) {
			roles[i] = new GrantedAuthorityImpl(this.user.getRoles().get(i).getRoleName());
		}
		return roles;
	}

	/**
	 * ��ȡ�û�����
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
