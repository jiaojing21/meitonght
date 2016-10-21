package com.itsv.platform.system.chooseuser.dao;

import com.itsv.platform.system.chooseuser.vo.UserRole;
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
public class UserRoleDao extends HibernatePagedDao<UserRole> {
}
