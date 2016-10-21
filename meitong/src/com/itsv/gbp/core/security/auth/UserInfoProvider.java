package com.itsv.gbp.core.security.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

import com.itsv.gbp.core.security.vo.RoleVO;
import com.itsv.gbp.core.security.vo.UserVO;

/**
 * �û���Ϣ�ṩ�ࡣ��������ʾ�࣬Ӧ����Ӧ�����þ����û���Ȩ��ģ�顣
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-28 ����12:07:01
 * @version 1.0
 */
public class UserInfoProvider implements UserDetailsService {

	private Map<String, UserVO> map = new HashMap<String, UserVO>();

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,
			DataAccessException {
		if (map.containsKey(username)) {
			return new UserAdapterVO(map.get(username));
		}

		throw new UsernameNotFoundException(username + " is not found.");
	}

	//��ʼ��������Ԥ��׼�������û�����
	public void init() {
		UserVO user1 = new UserVO(1, "fly", "fly");
		List<RoleVO> roles1 = new ArrayList<RoleVO>();
		roles1.add(new RoleVO(1, "ROLE_A"));
		roles1.add(new RoleVO(2, "ROLE_B"));
		user1.setRoles(roles1);
		map.put(user1.getUserName(), user1);

		UserVO user2 = new UserVO(2, "admin", "admin");
		List<RoleVO> roles2 = new ArrayList<RoleVO>();
		roles2.add(new RoleVO(3, "ROLE_A"));
		user2.setRoles(roles2);
		map.put(user2.getUserName(), user2);
	}
}
