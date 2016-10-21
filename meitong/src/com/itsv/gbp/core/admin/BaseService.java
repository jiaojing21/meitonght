package com.itsv.gbp.core.admin;

import com.itsv.gbp.core.admin.security.UserInfoAdapter;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.security.util.SecureTool;

/**
 * service���ࡣ֮����Է����ȡ��ǰ��¼�û���Ϣ��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-20 ����12:25:43
 * @version 1.0
 */
public class BaseService {

	/**
	 * �Ӱ�ȫģ���л�ȡ��ǰ��¼���û���Ϣ
	 * 
	 * @return
	 */
	protected User getCurrentUser() {
		UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
		return (adapter == null ? null : adapter.getRealUser());
	}

	/**
	 * ��ȡ��ǰ�û���
	 * @return
	 */
	protected String getUserId() {
		User user = getCurrentUser();
		return (user == null ? null : user.getId());
	}
}
