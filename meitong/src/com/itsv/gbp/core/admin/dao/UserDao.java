package com.itsv.gbp.core.admin.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.admin.vo.Role;
import com.itsv.gbp.core.admin.vo.Unit;
import com.itsv.gbp.core.admin.vo.User;
import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.platform.common.messageMgr.vo.Itsv_message;

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
public class UserDao extends HibernatePagedDao<User> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserDao.class);

	public Unit getUnit(User user){
		String sql = "from Unit t where t.id = '" + String.valueOf(user.getUnitId())+"'";
		Unit unit = (Unit)this.getHibernateTemplate().find(sql).get(0);
		return unit;
	}
	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((User) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((User) o);

		super.update(o);
	}

	//	�ж��Ƿ�����ͬ�û����Ķ������
	private void check(User user) throws OrmException {
		if (isNotUnique(user, "userName")) {
			logger.info("���û���[" + user.getUserName() + "]�Ѿ�����");
			throw new OrmException("���û���[" + user.getUserName() + "]�Ѿ�����");
		}
	}

	//�����û�����ѯ����Ӧ�˵�
	public List findMenusByUser(User user) {
		List<Role> roles = user.getRoles();
		if (roles == null || roles.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		StringBuffer hsql = new StringBuffer("select distinct m from Menu m,RoleMenu rm where m.enabled = true and m.id=rm.menuId and (");
		List<String> params = new ArrayList<String>();
		for (Role role : roles) {
			if (!params.isEmpty()) {
				hsql.append(" or ");
			}
			hsql.append(" rm.roleId = ? ");
			params.add(role.getId());
		}
		hsql.append(") order by m.code");

		return find(hsql.toString(), params.toArray());
	}

	//�����û�����ѯ����Ӧ�˵�
	//Group used
	public List findMenusByUser(List<Role> roles) {
		if (roles == null || roles.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		StringBuffer hsql = new StringBuffer("select distinct m from Menu m,RoleMenu rm where m.enabled = true and m.id=rm.menuId and (");
		List<String> params = new ArrayList<String>();
		for (Role role : roles) {
			if (!params.isEmpty()) {
				hsql.append(" or ");
			}
			hsql.append(" rm.roleId = ? ");
			params.add(role.getId());
		}
		hsql.append(") order by m.code");

		return find(hsql.toString(), params.toArray());
	}
	
	/**
	 * ���������ѯ
	 */
	public List<User> queryByObject(User user) {
		return find(buildCriteriaByVO(user));
	}

	  private DetachedCriteria buildCriteriaByVO(User user) {
		DetachedCriteria dc = createDetachedCriteria();

		if (user.getId() != null) {
			dc.add(Restrictions.eq("id", user.getId()));
		}

		if (user.getUnitId() != null) {
			dc.add(Restrictions.eq("unitId", user.getUnitId()));
		}

		if (user.getUserName() != null && user.getUserName().length() > 0) {
			dc.add(Restrictions.like("name", user.getUserName(),
					MatchMode.ANYWHERE));
		}

		dc.addOrder(Order.asc("unitId"));
		dc.addOrder(Order.asc("sortno"));

		return dc;
	}
}
