package com.itsv.platform.system.chooseunit.bo;

import java.util.List;

import org.apache.log4j.Logger;

import com.itsv.gbp.core.admin.bo.LoggedService;
import com.itsv.platform.system.chooseunit.dao.ChooseUnitDao;
import com.itsv.platform.system.chooseunit.vo.ChooseUnit;

/**
 * 处理对单位对象的业务操作。<br>
 * 单位对象与用户对象之间不建立关联关系。
 * 
 * 该类主要演示service之间允许相互调用。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 下午04:25:01
 * @version 1.0
 */
public class ChooseUnitService extends LoggedService {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ChooseUnitService.class);

	private ChooseUnitDao unitDao;
		
	//杨文彦2007-07-31添加方法
	/**
	 * 检索出所有的单位信息。不包括已作废的单位
	 */
	public List<ChooseUnit> queryEnabledUnit() {
		return this.unitDao.findBy("enabled", true);
	}

	public List<ChooseUnit> queryAll() {
		return this.unitDao.getAll();
	}

	/** get,set */
	public void setUnitDao(ChooseUnitDao unitDao) {
		this.unitDao = unitDao;
	}

}
