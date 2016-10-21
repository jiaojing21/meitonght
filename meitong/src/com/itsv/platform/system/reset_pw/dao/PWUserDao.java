package com.itsv.platform.system.reset_pw.dao;

import org.apache.log4j.Logger;

import com.itsv.platform.system.reset_pw.vo.PWUser;
import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;

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
public class PWUserDao extends HibernatePagedDao<PWUser> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PWUserDao.class);

	@Override
	public void update(Object o) throws OrmException {
		// 进行基本校验
		check((PWUser) o);

		super.update(o);
	}

	// 判断是否有相同用户名的对象存在
	private void check(PWUser user) throws OrmException {
		if (isNotUnique(user, "userName")) {
			logger.info("该用户名[" + user.getUserName() + "]已经存在");
			throw new OrmException("该用户名[" + user.getUserName() + "]已经存在");
		}
	}
}
