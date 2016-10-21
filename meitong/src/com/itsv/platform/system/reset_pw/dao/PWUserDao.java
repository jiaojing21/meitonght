package com.itsv.platform.system.reset_pw.dao;

import org.apache.log4j.Logger;

import com.itsv.platform.system.reset_pw.vo.PWUser;
import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;

/**
 * �û���������ݷ����ࡣ<br>
 * 
 * ��ʾͨ�������ṩ�ķ�����Ѹ�ټ����û����Ƿ��ظ���isNotUnique������������ų���ǰ����<br>
 * �û����ɫ�Ĺ�ϵ��hibernate���������Զ�ά����
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����03:27:10
 * @version 1.0
 */
public class PWUserDao extends HibernatePagedDao<PWUser> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PWUserDao.class);

	@Override
	public void update(Object o) throws OrmException {
		// ���л���У��
		check((PWUser) o);

		super.update(o);
	}

	// �ж��Ƿ�����ͬ�û����Ķ������
	private void check(PWUser user) throws OrmException {
		if (isNotUnique(user, "userName")) {
			logger.info("���û���[" + user.getUserName() + "]�Ѿ�����");
			throw new OrmException("���û���[" + user.getUserName() + "]�Ѿ�����");
		}
	}
}
