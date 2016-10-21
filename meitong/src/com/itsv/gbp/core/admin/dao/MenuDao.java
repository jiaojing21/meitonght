package com.itsv.gbp.core.admin.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.itsv.gbp.core.admin.vo.Menu;
import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.util.TreeCodeTool;

/**
 * 菜单对象的数据访问类。<br>
 * 
 * 演示在删除菜单时通过Hsql检查是否已有角色使用。<br>
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 下午03:25:04
 * @version 1.0
 */
public class MenuDao extends HibernatePagedDao<Menu> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MenuDao.class);

	//菜单层次码的编码格式
	private String codePattern = "3-3-3-3";

	@Override
	public void save(Object o) throws OrmException {
		Menu menu = (Menu) o;
		//判断层次码格式是否正确
		if (!TreeCodeTool.isValidate(menu.getCode(), codePattern)) {
			logger.info("该菜单代码[" + menu.getCode() + "]不符合编码格式[" + codePattern + "]");
			throw new OrmException("该菜单代码[" + menu.getCode() + "]不符合编码格式[" + codePattern + "]");
		}

		//判断是否有相同代码的菜单项
		if (isNotUnique(menu, "code")) {
			logger.info("该菜单代码[" + menu.getCode() + "]已经存在");
			throw new OrmException("该菜单代码[" + menu.getCode() + "]已经存在");
		}

		//判断父编码是否存在
		String fatherCode = TreeCodeTool.getFatherCode(menu.getCode(), codePattern);
		if (null != fatherCode && fatherCode.length() > 0) {
			Menu father = findUniqueBy("code", fatherCode);
			if (null == father) {
				logger.info("该菜单的上级菜单[" + fatherCode + "]不存在");
				throw new OrmException("该菜单的上级菜单[" + fatherCode + "]不存在");

			}
		}

		//设置编码级次及是否底级
		menu.setIdClass(TreeCodeTool.getLevel(menu.getCode(), codePattern));
		menu.setLeaf(true);

		// 保存记录
		super.save(o);

		//处理父编码的级次
		confirmLeaf(fatherCode);
	}

	@Override
	public void update(Object o) throws OrmException {
		Menu menu = (Menu) o;

		//取出传来的旧编码
		String oldCode = menu.getOthers().getStrParam1();
		//如果修改了层次码，则需要做编码校验
		if (!menu.getCode().equals(oldCode)) {

			//1 判断层次码格式是否正确
			if (!TreeCodeTool.isValidate(menu.getCode(), codePattern)) {
				logger.info("该菜单代码[" + menu.getCode() + "]不符合编码格式[" + codePattern + "]");
				throw new OrmException("该菜单代码[" + menu.getCode() + "]不符合编码格式[" + codePattern + "]");
			}

			//2 判断原菜单项下是否有子菜单
			String hsql = "select count(*) from Menu where code!='" + oldCode + "' and code like '" + oldCode
					+ "%'";
			long count = (Long) getFirst(find(hsql));
			if (count > 0) {
				logger.info("菜单[" + oldCode + "]下有子菜单，不允许变更编码");
				throw new OrmException("菜单[" + oldCode + "]下有子菜单，不允许变更编码");
			}

			//3 判断是否有与新编码相同的菜单项
			if (isNotUnique(menu, "code")) {
				logger.info("该菜单代码[" + menu.getCode() + "]已经存在");
				throw new OrmException("该菜单代码[" + menu.getCode() + "]已经存在");
			}

			//4 判断新编码的父编码是否存在
			String fatherCode = TreeCodeTool.getFatherCode(menu.getCode(), codePattern);
			if (null != fatherCode && fatherCode.length() > 0) {
				Menu father = findUniqueBy("code", fatherCode);
				if (null == father) {
					logger.info("该菜单的上级菜单[" + fatherCode + "]不存在");
					throw new OrmException("该菜单的上级菜单[" + fatherCode + "]不存在");
				}
			}

			//如果修改了编码，则一定为底级
			menu.setLeaf(true);
		}

		//设置编码级次
		menu.setIdClass(TreeCodeTool.getLevel(menu.getCode(), codePattern));

		super.update(o);

		if (!menu.getCode().equals(oldCode)) {
			//处理旧的父菜单
			confirmLeaf(TreeCodeTool.getFatherCode(oldCode, codePattern));

			//处理新的父菜单
			confirmLeaf(TreeCodeTool.getFatherCode(menu.getCode(), codePattern));
		}
	}

	@Override
	public void removeById(Serializable id) throws OrmException {
		// 判断该菜单是否已分配给角色
		String hsql = "select count(*) from RoleMenu where menuId = ?";
		long count = (Long) getFirst(find(hsql, id));
		if (count > 0) {
			logger.info("删除菜单" + id + "失败：该菜单已分配给 " + count + " 个角色使用");
			throw new OrmException("该菜单已分配给 " + count + " 个角色使用");
		}

		//首先获得该菜单的信息
		Menu menu = get(id);
		if (null == menu) {
			logger.info("菜单[" + id + "]不存在或已经被删除。");
			throw new OrmException("菜单[" + id + "]不存在或已经被删除。");
		}

		//如果菜单有子菜单，则不允许删除
		hsql = "select count(*) from Menu where code!='" + menu.getCode() + "' and code like '"
				+ menu.getCode() + "%'";
		count = (Long) getFirst(find(hsql));
		if (count > 0) {
			logger.info("菜单[" + menu.getCode() + "]有子菜单项。请先删除子菜单项");
			throw new OrmException("菜单[" + menu.getCode() + "]有子菜单项。请先删除子菜单项");
		}

		super.removeById(id);

		//处理父菜单项是否底级
		confirmLeaf(TreeCodeTool.getFatherCode(menu.getCode(), codePattern));
	}

	/**
	 * 根据给定的层次码，确定该层次码是否为最底级
	 * 
	 * @param treeCode
	 */
	private void confirmLeaf(String treeCode) {
		if (treeCode == null || treeCode.length() == 0) {
			return;
		}

		Menu menu = findUniqueBy("code", treeCode);
		String hsql = "select count(*) from Menu where code <> '" + menu.getCode() + "' and code like '"
				+ menu.getCode() + "%'";
		long count = (Long) getFirst(find(hsql));

		if (count == 0 && menu.getLeaf() == Boolean.FALSE) {
			menu.setLeaf(true);
		}

		if (count > 0 && menu.getLeaf() == Boolean.TRUE) {
			menu.setLeaf(false);
		}
	}
}
