package com.itsv.gbp.core.admin.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.vo.Role;
import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;

/**
 * 角色对象的数据访问类。<br>
 * 
 * 只维护角色本身的信息。不维护角色与菜单对应关系。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 下午03:25:32
 * @version 1.0
 */
public class RoleDao extends HibernatePagedDao<Role> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RoleDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//进行基本校验
		check((Role) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//进行基本校验
		check((Role) o);

		super.update(o);
	}

	//	判断是否有相同登录名称的角色名存在
	private void check(Role role) throws OrmException {
		if (isNotUnique(role, "name")) {
			logger.info("该角色名[" + role.getName() + "]已经存在");
			throw new OrmException("该角色名[" + role.getName() + "]已经存在");
		}
	}

	@Override
	public void removeById(Serializable id) throws OrmException {
		// 判断该角色是否已分配给用户
		String hsql = "select count(*) from User as u inner join u.roles as t where t.id = ?";
		long count = (Long) getFirst(find(hsql, id));
		if (count > 0) {
			logger.info("删除角色" + id + "失败：该角色已分配给 " + count + " 个用户使用");
			//throw new AppException("角色[" + id + "]已分配给 " + count + " 个用户使用");
			throw new AppException("该角色已分配给 " + count + " 个用户使用！");
		}

		super.removeById(id);
	}
}
