package com.itsv.platform.common.dictionary.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;

import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;

/**
 * 数据字典对象的数据访问类
 * 
 * 
 * @author milu
 * @since 2007-07-22
 * @version 1.0
 */
public class Itsv_dictionaryDao extends HibernatePagedDao<Itsv_dictionary> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(Itsv_dictionaryDao.class);

	@Override
	public void save(Object o) throws OrmException {
		// 进行基本校验
		check((Itsv_dictionary) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		// 进行基本校验
		check((Itsv_dictionary) o);

		super.update(o);
	}

	/**
	 * 组合条件查询
	 */
	public List<Itsv_dictionary> queryByObject(Itsv_dictionary itsv_dictionary) {
		return find(buildCriteriaByVO(itsv_dictionary));
	}

	/**
	 * 分页查询。<br>
	 * 只有总记录数为-1时，才会应用传来的参数对象，否则使用前一次的查询参数。
	 */
	public IPagedList queryByObject(IPagedList records,
			Itsv_dictionary itsv_dictionary) {
		// 如果totalNum=-1，则将传来的参数构造为DetachedCriteria对象，并存入IPagedList的param属性之中
		if (records.getTotalNum() == -1) {
			records.setParam(buildCriteriaByVO(itsv_dictionary));
		}

		return pagedQuery(records, (DetachedCriteria) records.getParam());
	}

	private DetachedCriteria buildCriteriaByVO(Itsv_dictionary itsv_dictionary) {
		DetachedCriteria dc = createDetachedCriteria();

		// ID
		if (itsv_dictionary.getId() != null) {
			dc.add(Restrictions.eq("id", itsv_dictionary.getId()));
		}

		// 父级编码
		if (itsv_dictionary.getParentcode() != null
				&& itsv_dictionary.getParentcode().length() > 0) {
			dc.add(Restrictions.eq("parentcode",
					itsv_dictionary.getParentcode()));
		}

		// 层级编码
		if (itsv_dictionary.getCode() != null
				&& itsv_dictionary.getCode().length() > 0) {
			dc.add(Restrictions.eq("code", itsv_dictionary.getCode()));
		}

		// 级次编号
		if (itsv_dictionary.getCodeclass() != null) {
			dc.add(Restrictions.eq("level", itsv_dictionary.getCodeclass()));
		}

		// 顺序编号
		if (itsv_dictionary.getDictno() != null) {
			dc.add(Restrictions.eq("dictno", itsv_dictionary.getDictno()));
		}

		// 字典名称
		if (itsv_dictionary.getDictname() != null
				&& itsv_dictionary.getDictname().length() > 0) {
			dc.add(Restrictions.like("dictname", itsv_dictionary.getDictname(),
					MatchMode.ANYWHERE));
		}

		// 字典描述
		if (itsv_dictionary.getDescription() != null
				&& itsv_dictionary.getDescription().length() > 0) {
			dc.add(Restrictions.like("description",
					itsv_dictionary.getDescription(), MatchMode.ANYWHERE));
		}

		// 业务编码
		if (itsv_dictionary.getHardcode() != null
				&& itsv_dictionary.getHardcode().length() > 0) {
			dc.add(Restrictions.eq("hardcode", itsv_dictionary.getHardcode()));
		}

		// 删除标志
		if (itsv_dictionary.getCandelete() != null) {
			dc.add(Restrictions.eq("candelete", itsv_dictionary.getCandelete()));
		}
		// 字典类型（0：默认单选，1：单选，2：多选）
		if (itsv_dictionary.getType() != null) {
			dc.add(Restrictions.eq("type", itsv_dictionary.getType()));
		}
		// 品牌（只有字典为《系列》时才会有品牌选项）
		if (itsv_dictionary.getBrandcode() != null) {
			dc.add(Restrictions.eq("brandcode", itsv_dictionary.getBrandcode()));
		}
		// 是否为外观（0：是；1：否）
		if (itsv_dictionary.getExterior() != null) {
			dc.add(Restrictions.eq("exterior", itsv_dictionary.getExterior()));
		}
		// 是否作为外部筛选条件（0：是；1：否）
		if (itsv_dictionary.getScreen() != null) {
			dc.add(Restrictions.eq("screen", itsv_dictionary.getScreen()));
		}

		return dc;
	}

	// 数据校验
	private void check(Itsv_dictionary itsv_dictionary) throws OrmException {

	}

}
