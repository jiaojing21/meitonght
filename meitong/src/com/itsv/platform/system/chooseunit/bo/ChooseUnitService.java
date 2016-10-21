package com.itsv.platform.system.chooseunit.bo;

import java.util.List;

import org.apache.log4j.Logger;

import com.itsv.gbp.core.admin.bo.LoggedService;
import com.itsv.platform.system.chooseunit.dao.ChooseUnitDao;
import com.itsv.platform.system.chooseunit.vo.ChooseUnit;

/**
 * ����Ե�λ�����ҵ�������<br>
 * ��λ�������û�����֮�䲻����������ϵ��
 * 
 * ������Ҫ��ʾservice֮�������໥���á�
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-6 ����04:25:01
 * @version 1.0
 */
public class ChooseUnitService extends LoggedService {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ChooseUnitService.class);

	private ChooseUnitDao unitDao;
		
	//������2007-07-31��ӷ���
	/**
	 * ���������еĵ�λ��Ϣ�������������ϵĵ�λ
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
