package com.itsv.gbp.core.group.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;

import com.itsv.gbp.core.admin.vo.Role;
import com.itsv.gbp.core.group.vo.Gbp_group;
import com.itsv.gbp.core.group.vo.Gbp_grouprole;

/**
 * ���������ݷ�����
 * 
 * 
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_groupDao extends HibernatePagedDao<Gbp_group> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Gbp_groupDao.class);

	@Override
	public void save(Object o) throws OrmException {
		// ���л���У��
		check((Gbp_group) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		// ���л���У��
		check((Gbp_group) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Gbp_group> queryByObject(Gbp_group gbp_group) {
		return find(buildCriteriaByVO(gbp_group));
	}

	/**
	 * ��ҳ��ѯ��<br>
	 * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
	 */
	public IPagedList queryByObject(IPagedList records, Gbp_group gbp_group) {
		// ���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
		if (records.getTotalNum() == -1) {
			records.setParam(buildCriteriaByVO(gbp_group));
		}

		return pagedQuery(records, (DetachedCriteria) records.getParam());
	}

	private DetachedCriteria buildCriteriaByVO(Gbp_group gbp_group) {
		DetachedCriteria dc = createDetachedCriteria();

		// ID
		if (gbp_group.getId() != null) {
			dc.add(Restrictions.eq("id", gbp_group.getId()));
		}

		// ��ע
		if (gbp_group.getGroupremarks() != null
				&& gbp_group.getGroupremarks().length() > 0) {
			dc.add(Restrictions.like("groupremarks", gbp_group
					.getGroupremarks(), MatchMode.ANYWHERE));
		}

		// ������
		if (gbp_group.getGroupname() != null
				&& gbp_group.getGroupname().length() > 0) {
			dc.add(Restrictions.like("groupname", gbp_group.getGroupname(),
					MatchMode.ANYWHERE));
		}

		return dc;
	}

	// ����У��
	private void check(Gbp_group gbp_group) throws OrmException {

	}

	public List<Gbp_grouprole> findGroupRolesByUserId(String id) {

		StringBuffer hsql = new StringBuffer(
				"select gr from  Gbp_grouprole gr,Gbp_groupuser gu " +
				"where gu.userid = ? and gu.groupid=gr.groupid");
		List<String> params = new ArrayList<String>();
		params.add(id);

		return find(hsql.toString(), params.toArray());
	}
}
