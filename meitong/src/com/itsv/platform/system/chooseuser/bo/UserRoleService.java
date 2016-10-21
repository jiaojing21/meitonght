package com.itsv.platform.system.chooseuser.bo;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.itsv.platform.system.chooseuser.dao.UserRoleDao;

import com.itsv.platform.system.chooseuser.vo.UserRole;

import com.itsv.gbp.core.admin.bo.*;

/**
 * ��ɫҵ���߼��ࡣ<br>
 * �ȴ����ɫ��Ҳ�����ɫ��˵�֮��Ĺ�����ϵ��<br>
 * �����ɫ���˵���ϵ�����仯����ʽ����Ȩ��ģ���RightProviderFactory�����Ȩ�����ݡ�
 * 
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����10:10:08
 * @version 1.0
 */
public class UserRoleService extends LoggedService implements
		ApplicationContextAware {

	/**
	 * Logger for this class
	 */

	private UserRoleDao roleDao;

	/**
	 * ���������н�ɫ��
	 */
	public List<UserRole> queryAll() {
		return this.roleDao.getAll();
	}

	/** get,set */
	public void setRoleDao(UserRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
	}
}
