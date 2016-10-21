package com.itsv.platform.common.fileMgr.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.itsv.gbp.core.orm.hibernate.HibernatePagedDao;

import com.itsv.platform.common.fileMgr.vo.ZjWjys;

public class UploadFileDao extends HibernatePagedDao<ZjWjys> {

	public UploadFileDao() {
	}

	/**
	 * 读取类别名称列表信息
	 */
	public List getCclbList() {
		String sql = "from ZjWjlb z where z.zt = 1 or z.zt = 2";
		return select(sql);
	}

	/**
	 * 通过类别名称查询文件类别信息，只读取版本编号最大的文件类别信息
	 */
	public List getWjlbByLbmc(String lbmc) {
		String sql = "from ZjWjlb z where (z.zt = 1 or z.zt = 2) and z.cclbmc ='"
				+ lbmc
				+ "' and z.lbmcbb= (select max(t.lbmcbb) from ZjWjlb t where t.cclbmc ='"
				+ lbmc + "')";
		return select(sql);
	}

	/**
	 * 通过类别编号查询文件类别信息
	 */
	public List getWjlbByLbpk(String lbpk) {
		String sql = "from ZjWjlb z where z.cclbbhPk ='" + lbpk + "'";
		return select(sql);
	}

	/**
	 * 通过附件编号查询附件信息
	 */
	public List getWjysByFjid(String fileid) {
		String sql = "from ZjWjys z where z.ysbhPk ='" + fileid + "'";
		return select(sql);
	}

	/**
	 * 通过附件编号查询文件类别信息和文件信息
	 */
	public List getWjysAndWjlb(String fileid) {
		String sql = "from ZjWjys y , ZjWjlb l where y.ysbhPk='" + fileid
				+ "' and y.cclbbhPk=l.cclbbhPk";
		return select(sql);
	}

	/**
	 * 内部方法
	 */
	private List select(String sql) {
		return getHibernateTemplate().find(sql);
	}

	/**
	 * 插入文件映射表一条记录
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
	 * 修改文件映射表一条记录
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
	 * 删除文件映射表一条记录
	 */
	public boolean deleteFile(ZjWjys wjys) {
		this.remove(wjys);
		return true;
	}
}
