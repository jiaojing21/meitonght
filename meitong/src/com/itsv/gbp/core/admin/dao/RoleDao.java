package com.itsv.gbp.core.admin.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.vo.Role;
import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;

/**
 * ��ɫ��������ݷ����ࡣ<br>
 * 
 * ֻά����ɫ�������Ϣ����ά����ɫ��˵���Ӧ��ϵ��
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����03:25:32
 * @version 1.0
 */
public class RoleDao extends HibernatePagedDao<Role> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RoleDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((Role) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((Role) o);

		super.update(o);
	}

	//	�ж��Ƿ�����ͬ��¼���ƵĽ�ɫ������
	private void check(Role role) throws OrmException {
		if (isNotUnique(role, "name")) {
			logger.info("�ý�ɫ��[" + role.getName() + "]�Ѿ�����");
			throw new OrmException("�ý�ɫ��[" + role.getName() + "]�Ѿ�����");
		}
	}

	@Override
	public void removeById(Serializable id) throws OrmException {
		// �жϸý�ɫ�Ƿ��ѷ�����û�
		String hsql = "select count(*) from User as u inner join u.roles as t where t.id = ?";
		long count = (Long) getFirst(find(hsql, id));
		if (count > 0) {
			logger.info("ɾ����ɫ" + id + "ʧ�ܣ��ý�ɫ�ѷ���� " + count + " ���û�ʹ��");
			//throw new AppException("��ɫ[" + id + "]�ѷ���� " + count + " ���û�ʹ��");
			throw new AppException("�ý�ɫ�ѷ���� " + count + " ���û�ʹ�ã�");
		}

		super.removeById(id);
	}
}
