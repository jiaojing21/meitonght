package com.itsv.gbp.core.admin.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.itsv.gbp.core.admin.vo.Unit;
import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.util.TreeCodeTool;

/**
 * 单位对象的数据访问类
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 下午03:26:06
 * @version 1.0
 */
public class UnitDao extends HibernatePagedDao<Unit> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UnitDao.class);

	//单位层次码的编码格式
	private String codePattern = "3-3-3-3-3-3-3-3";

	@Override
	public void save(Object o) throws OrmException {
		Unit unit = (Unit) o;
		//判断层次码格式是否正确
		if (!TreeCodeTool.isValidate(unit.getCode(), codePattern)) {
			logger.info("该单位代码[" + unit.getCode() + "]不符合编码格式[" + codePattern + "]");
			throw new OrmException("该单位代码[" + unit.getCode() + "]不符合编码格式[" + codePattern + "]");
		}

		//判断是否有相同代码的单位项
		if (isNotUnique(unit, "code")) {
			logger.info("该单位代码[" + unit.getCode() + "]已经存在");
			throw new OrmException("该单位代码[" + unit.getCode() + "]已经存在");
		}

		//判断是否有相同代码的单位项
		if (isNotUnique(unit, "departno")) {
			logger.info("该单位编码[" + unit.getCode() + "]已经存在");
			throw new OrmException("该单位编码[" + unit.getDepartno() + "]已经存在");
		}

		//判断是否有相同名称的单位项
		String sql;
		if(unit.getParentid() != null && !unit.getParentid().equals("")){
			sql = "from Unit where parentid = '" + String.valueOf(unit.getParentid()) + "' and name = '" + unit.getName() + "'";
		}else{
			sql = "from Unit where parentid = null and name = '" + unit.getName() + "'";
		}
		List unitlist = find(sql);
		if (unitlist.size() > 0) {
			logger.info("该单位名称[" + unit.getName() + "]已经存在");
			throw new OrmException("该单位名称[" + unit.getName() + "]已经存在");
		}

		//判断父编码是否存在
		String fatherCode = TreeCodeTool.getFatherCode(unit.getCode(), codePattern);
		if (null != fatherCode && fatherCode.length() > 0) {
			Unit father = findUniqueBy("code", fatherCode);
			if (null == father) {
				logger.info("该单位的上级单位[" + fatherCode + "]不存在");
				throw new OrmException("该单位的上级单位[" + fatherCode + "]不存在");
			}
		}

		//设置编码级次及是否底级
		unit.setIdClass(TreeCodeTool.getLevel(unit.getCode(), codePattern));
		unit.setLeaf(true);

		// 保存记录
		super.save(o);

		//处理父编码的级次
		confirmLeaf(fatherCode);
	}

	public List select(String sql) {
		return getHibernateTemplate().find(sql);
	}
	
	@Override
	public void update(Object o) throws OrmException {
		Unit unit = (Unit) o;

		//取出传来的旧编码
		String oldCode = unit.getOthers().getStrParam1();
		//如果修改了层次码，则需要做编码校验
		if (!unit.getCode().equals(oldCode)) {

			//1 判断层次码格式是否正确
			if (!TreeCodeTool.isValidate(unit.getCode(), codePattern)) {
				logger.info("该单位代码[" + unit.getCode() + "]不符合编码格式[" + codePattern + "]");
				throw new OrmException("该单位代码[" + unit.getCode() + "]不符合编码格式[" + codePattern + "]");
			}

			//2 判断原单位项下是否有子单位
			String hsql = "select count(*) from Unit where code!='" + oldCode + "' and code like '" + oldCode
					+ "%'";
			long count = (Long) getFirst(find(hsql));
			if (count > 0) {
				logger.info("单位[" + oldCode + "]下有子单位，不允许变更编码");
				throw new OrmException("单位[" + oldCode + "]下有子单位，不允许变更编码");
			}

			//3 判断是否有与新编码相同的单位项
			if (isNotUnique(unit, "code")) {
				logger.info("该单位代码[" + unit.getCode() + "]已经存在");
				throw new OrmException("该单位代码[" + unit.getCode() + "]已经存在");
			}

			//判断是否有相同代码的单位项
			if (isNotUnique(unit, "departno")) {
				logger.info("该单位编码[" + unit.getCode() + "]已经存在");
				throw new OrmException("该单位编码[" + unit.getDepartno() + "]已经存在");
			}

			//判断是否有相同名称的单位项
			if (isNotUnique(unit, "name")) {
				logger.info("该单位名称[" + unit.getName() + "]已经存在");
				throw new OrmException("该单位名称[" + unit.getName() + "]已经存在");
			}
			
			//4 判断新编码的父编码是否存在
			String fatherCode = TreeCodeTool.getFatherCode(unit.getCode(), codePattern);
			if (null != fatherCode && fatherCode.length() > 0) {
				Unit father = findUniqueBy("code", fatherCode);
				if (null == father) {
					logger.info("该单位的上级单位[" + fatherCode + "]不存在");
					throw new OrmException("该单位的上级单位[" + fatherCode + "]不存在");
				}
			}

			//如果修改了编码，则一定为底级
			unit.setLeaf(true);
		}

		//设置编码级次
		unit.setIdClass(TreeCodeTool.getLevel(unit.getCode(), codePattern));

		super.update(o);

		if (!unit.getCode().equals(oldCode)) {
			//处理旧的父单位
			confirmLeaf(TreeCodeTool.getFatherCode(oldCode, codePattern));

			//处理新的父单位
			confirmLeaf(TreeCodeTool.getFatherCode(unit.getCode(), codePattern));
		}
	}

	@Override
	public void removeById(Serializable id) throws OrmException {
		//首先获得该单位的信息
		Unit unit = get(id);
		if (null == unit) {
			logger.info("单位[" + unit.getName() + "]不存在或已经被删除。");
			throw new OrmException("单位[" + unit.getName() + "]不存在或已经被删除。");
		}

		//如果单位有子单位，则不允许删除
		String hsql = "select count(*) from Unit where code!='" + unit.getCode() + "' and code like '"
				+ unit.getCode() + "%'";
		long count = (Long) getFirst(find(hsql));
		if (count > 0) {
			logger.info("单位[" + unit.getName() + "]有子单位项。请先删除子单位项");
			throw new OrmException("单位[" + unit.getName() + "]有子单位项。请先删除子单位项");
		}

		super.removeById(id);

		//处理父单位项是否底级
		confirmLeaf(TreeCodeTool.getFatherCode(unit.getCode(), codePattern));
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

		Unit unit = findUniqueBy("code", treeCode);
		String hsql = "select count(*) from Unit where code <> '" + unit.getCode() + "' and code like '"
				+ unit.getCode() + "%'";
		long count = (Long) getFirst(find(hsql));

		if (count == 0 && unit.getLeaf() == Boolean.FALSE) {
			unit.setLeaf(true);
		}

		if (count > 0 && unit.getLeaf() == Boolean.TRUE) {
			unit.setLeaf(false);
		}
	}
}
