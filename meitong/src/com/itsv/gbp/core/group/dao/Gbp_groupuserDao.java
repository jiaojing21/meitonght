package com.itsv.gbp.core.group.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;

import com.itsv.gbp.core.group.vo.Gbp_groupuser;

/**
 * gbp_groupuser��������ݷ�����
 * 
 * 
 * @author Ace8@yahoo.cn
 * @since 2008-09-02
 * @version 1.0
 */
public class Gbp_groupuserDao extends HibernatePagedDao<Gbp_groupuser> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(Gbp_groupuserDao.class);

	@Override
	public void save(Object o) throws OrmException {
		// ���л���У��
		check((Gbp_groupuser) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		// ���л���У��
		check((Gbp_groupuser) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<Gbp_groupuser> queryByObject(Gbp_groupuser gbp_groupuser) {
		return find(buildCriteriaByVO(gbp_groupuser));
	}

	/**
	 * ��ҳ��ѯ��<br>
	 * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
	 */
	public IPagedList queryByObject(IPagedList records,
			Gbp_groupuser gbp_groupuser) {
		// ���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
		if (records.getTotalNum() == -1) {
			records.setParam(buildCriteriaByVO(gbp_groupuser));
		}

		return pagedQuery(records, (DetachedCriteria) records.getParam());
	}

	private DetachedCriteria buildCriteriaByVO(Gbp_groupuser gbp_groupuser) {
		DetachedCriteria dc = createDetachedCriteria();

		// ID
		if (gbp_groupuser.getId() != null) {
			dc.add(Restrictions.eq("id", gbp_groupuser.getId()));
		}

		// userid
		if (gbp_groupuser.getUserid() != null
				&& gbp_groupuser.getUserid().length() > 0) {
			dc.add(Restrictions.like("userid", gbp_groupuser.getUserid(),
					MatchMode.ANYWHERE));
		}

		// groupid
		if (gbp_groupuser.getGroupid() != null
				&& gbp_groupuser.getGroupid().length() > 0) {
			dc.add(Restrictions.like("groupid", gbp_groupuser.getGroupid(),
					MatchMode.ANYWHERE));
		}

		return dc;
	}

	// ����У��
	private void check(Gbp_groupuser gbp_groupuser) throws OrmException {

	}

	// @TODO
	public void removeByGroupId(String groupId) {
		Gbp_groupuser gbp_groupuser = new Gbp_groupuser();
		gbp_groupuser.setGroupid(groupId);
		try {
			List <Gbp_groupuser> list = this.queryByObject(gbp_groupuser);
			if(list!=null){
				for(Gbp_groupuser gu:list){
					this.remove(gu);
				}
			}
			
		} catch (Exception e) {
			logger.error("removeByGroupIdʱ����", e);
			e.printStackTrace();
		}
	}

}
