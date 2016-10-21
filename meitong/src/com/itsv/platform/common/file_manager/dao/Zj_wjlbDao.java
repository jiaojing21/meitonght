package com.itsv.platform.common.file_manager.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;

import com.itsv.platform.common.fileMgr.vo.ZjWjlb;

/**
 * �洢�����Ϣ��������ݷ�����
 * 
 * 
 * @author milu
 * @since 2007-07-17
 * @version 1.0
 */
public class Zj_wjlbDao extends HibernatePagedDao<ZjWjlb> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Zj_wjlbDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((ZjWjlb) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((ZjWjlb) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<ZjWjlb> queryByObject(ZjWjlb ZjWjlb) {
		return find(buildCriteriaByVO(ZjWjlb));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, ZjWjlb ZjWjlb) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(ZjWjlb));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(ZjWjlb ZjWjlb){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (ZjWjlb.getId() != null ) {
    	dc.add(Restrictions.eq("id", ZjWjlb.getId()));
    }	

    //�洢������� 
    if (ZjWjlb.getCclbmc() != null && ZjWjlb.getCclbmc().length() > 0) {
    	dc.add(Restrictions.like("cclbmc", ZjWjlb.getCclbmc(), MatchMode.ANYWHERE));
    }	

    //�洢���汾 
    if (ZjWjlb.getLbmcbb() != null) {
    	dc.add(Restrictions.eq("lbmcbb", ZjWjlb.getLbmcbb()));
      }		

    //�洢��Ŀ¼ 
    if (ZjWjlb.getCcgml() != null && ZjWjlb.getCcgml().length() > 0) {
    	dc.add(Restrictions.like("ccgml", ZjWjlb.getCcgml(), MatchMode.ANYWHERE));
    }	

    //����Ŀ¼���� 
    if (ZjWjlb.getEjmlgz() != null && ZjWjlb.getEjmlgz().length() > 0) {
    	dc.add(Restrictions.like("ejmlgz", ZjWjlb.getEjmlgz(), MatchMode.ANYWHERE));
    }	

    //״̬ 
    if (ZjWjlb.getZt() != null && ZjWjlb.getZt().length() > 0) {
    	dc.add(Restrictions.like("zt", ZjWjlb.getZt(), MatchMode.ANYWHERE));
    }	

    //��ע 
    if (ZjWjlb.getBz() != null && ZjWjlb.getBz().length() > 0) {
    	dc.add(Restrictions.like("bz", ZjWjlb.getBz(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(ZjWjlb ZjWjlb) throws OrmException {

  }

}
