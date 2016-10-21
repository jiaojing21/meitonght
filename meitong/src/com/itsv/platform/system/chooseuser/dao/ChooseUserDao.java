package com.itsv.platform.system.chooseuser.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.platform.system.chooseuser.vo.ChooseUser;

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
public class ChooseUserDao extends HibernatePagedDao<ChooseUser> {

	public List<ChooseUser> queryByObject(ChooseUser user) {
		return find(buildCriteriaByVO(user));
	}

	private DetachedCriteria buildCriteriaByVO(ChooseUser user) {
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
