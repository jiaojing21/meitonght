package com.itsv.platform.system.chooseuser.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.platform.system.chooseuser.vo.ChooseUser;

/**
 * 用户对象的数据访问类。<br>
 * 
 * 演示通过基类提供的方法，迅速检验用户名是否重复。isNotUnique这个方法可以排除当前对象。<br>
 * 用户与角色的关系由hibernate关联管理自动维护。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 下午03:27:10
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
