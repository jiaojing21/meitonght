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
 * �����ֵ��������ݷ�����
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
		// ���л���У��
		check((Itsv_dictionary) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		// ���л���У��
		check((Itsv_dictionary) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Itsv_dictionary> queryByObject(Itsv_dictionary itsv_dictionary) {
		return find(buildCriteriaByVO(itsv_dictionary));
	}

	/**
	 * ��ҳ��ѯ��<br>
	 * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
	 */
	public IPagedList queryByObject(IPagedList records,
			Itsv_dictionary itsv_dictionary) {
		// ���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
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

		// ��������
		if (itsv_dictionary.getParentcode() != null
				&& itsv_dictionary.getParentcode().length() > 0) {
			dc.add(Restrictions.eq("parentcode",
					itsv_dictionary.getParentcode()));
		}

		// �㼶����
		if (itsv_dictionary.getCode() != null
				&& itsv_dictionary.getCode().length() > 0) {
			dc.add(Restrictions.eq("code", itsv_dictionary.getCode()));
		}

		// ���α��
		if (itsv_dictionary.getCodeclass() != null) {
			dc.add(Restrictions.eq("level", itsv_dictionary.getCodeclass()));
		}

		// ˳����
		if (itsv_dictionary.getDictno() != null) {
			dc.add(Restrictions.eq("dictno", itsv_dictionary.getDictno()));
		}

		// �ֵ�����
		if (itsv_dictionary.getDictname() != null
				&& itsv_dictionary.getDictname().length() > 0) {
			dc.add(Restrictions.like("dictname", itsv_dictionary.getDictname(),
					MatchMode.ANYWHERE));
		}

		// �ֵ�����
		if (itsv_dictionary.getDescription() != null
				&& itsv_dictionary.getDescription().length() > 0) {
			dc.add(Restrictions.like("description",
					itsv_dictionary.getDescription(), MatchMode.ANYWHERE));
		}

		// ҵ�����
		if (itsv_dictionary.getHardcode() != null
				&& itsv_dictionary.getHardcode().length() > 0) {
			dc.add(Restrictions.eq("hardcode", itsv_dictionary.getHardcode()));
		}

		// ɾ����־
		if (itsv_dictionary.getCandelete() != null) {
			dc.add(Restrictions.eq("candelete", itsv_dictionary.getCandelete()));
		}
		// �ֵ����ͣ�0��Ĭ�ϵ�ѡ��1����ѡ��2����ѡ��
		if (itsv_dictionary.getType() != null) {
			dc.add(Restrictions.eq("type", itsv_dictionary.getType()));
		}
		// Ʒ�ƣ�ֻ���ֵ�Ϊ��ϵ�С�ʱ�Ż���Ʒ��ѡ�
		if (itsv_dictionary.getBrandcode() != null) {
			dc.add(Restrictions.eq("brandcode", itsv_dictionary.getBrandcode()));
		}
		// �Ƿ�Ϊ��ۣ�0���ǣ�1����
		if (itsv_dictionary.getExterior() != null) {
			dc.add(Restrictions.eq("exterior", itsv_dictionary.getExterior()));
		}
		// �Ƿ���Ϊ�ⲿɸѡ������0���ǣ�1����
		if (itsv_dictionary.getScreen() != null) {
			dc.add(Restrictions.eq("screen", itsv_dictionary.getScreen()));
		}

		return dc;
	}

	// ����У��
	private void check(Itsv_dictionary itsv_dictionary) throws OrmException {

	}

}
