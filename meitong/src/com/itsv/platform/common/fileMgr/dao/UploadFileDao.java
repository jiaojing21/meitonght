package com.itsv.platform.common.fileMgr.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;

import com.itsv.platform.common.fileMgr.vo.ZjWjys;

public class UploadFileDao extends HibernatePagedDao<ZjWjys> {

	public UploadFileDao() {
	}

	/**
	 * ��ȡ��������б���Ϣ
	 */
	public List getCclbList() {
		String sql = "from ZjWjlb z where z.zt = 1 or z.zt = 2";
		return select(sql);
	}

	/**
	 * ͨ��������Ʋ�ѯ�ļ������Ϣ��ֻ��ȡ�汾��������ļ������Ϣ
	 */
	public List getWjlbByLbmc(String lbmc) {
		String sql = "from ZjWjlb z where (z.zt = 1 or z.zt = 2) and z.cclbmc ='"
				+ lbmc
				+ "' and z.lbmcbb= (select max(t.lbmcbb) from ZjWjlb t where t.cclbmc ='"
				+ lbmc + "')";
		return select(sql);
	}

	/**
	 * ͨ������Ų�ѯ�ļ������Ϣ
	 */
	public List getWjlbByLbpk(String lbpk) {
		String sql = "from ZjWjlb z where z.cclbbhPk ='" + lbpk + "'";
		return select(sql);
	}

	/**
	 * ͨ��������Ų�ѯ������Ϣ
	 */
	public List getWjysByFjid(String fileid) {
		String sql = "from ZjWjys z where z.ysbhPk ='" + fileid + "'";
		return select(sql);
	}

	/**
	 * ͨ��������Ų�ѯ�ļ������Ϣ���ļ���Ϣ
	 */
	public List getWjysAndWjlb(String fileid) {
		String sql = "from ZjWjys y , ZjWjlb l where y.ysbhPk='" + fileid
				+ "' and y.cclbbhPk=l.cclbbhPk";
		return select(sql);
	}

	/**
	 * �ڲ�����
	 */
	private List select(String sql) {
		return getHibernateTemplate().find(sql);
	}

	/**
	 * �����ļ�ӳ���һ����¼
	 */
	public boolean insertFj(ZjWjys wjys) {
		boolean flag = false;
		try {
			this.save(wjys);
			flag = true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * �޸��ļ�ӳ���һ����¼
	 */
	public boolean updateFj(ZjWjys wjys) {
		boolean flag = false;
		try {
			this.update(wjys);
			flag = true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * ɾ���ļ�ӳ���һ����¼
	 */
	public boolean deleteFile(ZjWjys wjys) {
		this.remove(wjys);
		return true;
	}
}
