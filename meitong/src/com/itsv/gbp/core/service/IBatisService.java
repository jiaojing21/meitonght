package com.itsv.gbp.core.service;

import javax.sql.DataSource;

import com.itsv.gbp.core.orm.ibatis.IbatisBaseDao;

/**
 * 基于iBatis DAO层的业务逻辑层基类
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-5 下午03:08:18
 * @version 1.0
 */
public class IBatisService {

	private String namespace;

	private IbatisBaseDao dao;

	public void setDataSource(DataSource dataSource) {
		this.dao.setDataSource(dataSource);
	}

	public IbatisBaseDao getDao() {
		return dao;
	}

	public void setDao(IbatisBaseDao dao) {
		this.dao = dao;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

}
