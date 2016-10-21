package com.itsv.annotation.fileUpload.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itsv.gbp.core.orm.OrmException;
import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;
import com.itsv.annotation.fileUpload.vo.FileUpload;

/**
 * file_upload��������ݷ�����
 * 
 * 
 * @author swk
 * @since 2016-03-24
 * @version 1.0
 */
 @Repository @Transactional
public class FileUploadDao extends HibernatePagedDao<FileUpload> {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FileUploadDao.class);

	@Override
	public void save(Object o) throws OrmException {
		//���л���У��
		check((FileUpload) o);

		super.save(o);
	}

	@Override
	public void update(Object o) throws OrmException {
		//���л���У��
		check((FileUpload) o);

		super.update(o);
	}

	/**
	 * ���������ѯ
	 */
	public List<FileUpload> queryByObject(FileUpload fileUpload) {
		return find(buildCriteriaByVO(fileUpload));
	}

  /**
   * ��ҳ��ѯ��<br>
   * ֻ���ܼ�¼��Ϊ-1ʱ���Ż�Ӧ�ô����Ĳ������󣬷���ʹ��ǰһ�εĲ�ѯ������
   */
  public IPagedList queryByObject(IPagedList records, FileUpload fileUpload) {
    //���totalNum=-1���򽫴����Ĳ�������ΪDetachedCriteria���󣬲�����IPagedList��param����֮��
    if (records.getTotalNum() == -1) {
  	  records.setParam(buildCriteriaByVO(fileUpload));
    }

    return pagedQuery(records, (DetachedCriteria) records.getParam());
  }

  private DetachedCriteria buildCriteriaByVO(FileUpload fileUpload){
		DetachedCriteria dc = createDetachedCriteria();

    //ID
    if (fileUpload.getId() != null ) {
    	dc.add(Restrictions.eq("id", fileUpload.getId()));
    }	


    //file_name 
    if (fileUpload.getFileName() != null && fileUpload.getFileName().length() > 0) {
    	dc.add(Restrictions.eq("currentName", fileUpload.getFileName()));
    }	

    //file_time 
//    if (fileUpload.getFileTime() != null) {
//    	dc.add(Restrictions.eq("fileTime", fileUpload.getFileTime()));
//      }		

    //downloadpath 
    if (fileUpload.getDownloadPath() != null && fileUpload.getDownloadPath().length() > 0) {
    	dc.add(Restrictions.like("downloadPath", fileUpload.getDownloadPath(), MatchMode.ANYWHERE));
    }	

    //fid 
    if (fileUpload.getFId() != null && fileUpload.getFId().length() > 0) {
    	dc.add(Restrictions.like("fId", fileUpload.getFId(), MatchMode.ANYWHERE));
    }	

    //operator 
    if (fileUpload.getOperator() != null && fileUpload.getOperator().length() > 0) {
    	dc.add(Restrictions.like("operator", fileUpload.getOperator(), MatchMode.ANYWHERE));
    }	

    //type 
    if (fileUpload.getType() != null && fileUpload.getType().length() > 0) {
    	dc.add(Restrictions.like("type", fileUpload.getType(), MatchMode.ANYWHERE));
    }	

    return dc;  
  }

  //����У��
  private void check(FileUpload fileUpload) throws OrmException {

  }
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	     super.setSessionFactory(sessionFactory);
	}
}
