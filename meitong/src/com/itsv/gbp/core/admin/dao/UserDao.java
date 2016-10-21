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
 * 用户对象的数据访问类。<br>
 * 
 * 演示通过基类提供的方法，迅速检验用户名是否重复。isNotUnique这个方法可以排除当前对象。<br>
 * 用户与角色的关系由hibernate关联管理自动维护。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 下午03:27:10
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
		//进行基本校验
		check((User) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((User) o);

		super.update(o);
	}

	//	判断是否有相同用户名的对象存在
	private void check(User user) throws OrmException {
		if (isNotUnique(user, "userName")) {
			logger.info("该用户名[" + user.getUserName() + "]已经存在");
			throw new OrmException("该用户名[" + user.getUserName() + "]已经存在");
		}
	}

	//根据用户名查询出相应菜单
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

	//根据用户名查询出相应菜单
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
	 * 组合条件查询
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
