package com.itsv.gbp.core.admin.bo;

import java.util.List;

import org.apache.log4j.Logger;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.dao.UnitDao;
import com.itsv.gbp.core.admin.vo.Unit;

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
public class UnitService extends LoggedService {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UnitService.class);

	private UnitDao unitDao;

	private UserService userService;

	/**
	 * ���ӵ�λ
	 */
	public void add(Unit unit) {
		Unit tmpUnit;
		//��ѯ�����ò㼶����
		String sql = "";
		if(unit.getParentid() == null || "".equalsIgnoreCase(String.valueOf(unit.getParentid()))){
			sql = "from Unit where idclass = 1";
		}else{
			sql = "from Unit where parentid = '" + String.valueOf(unit.getParentid())+"'";
		}
		List list = this.unitDao.select(sql);
		String maxCode = "001";
		for(int i = 0; i < list.size(); i++){
			tmpUnit = (Unit)list.get(i);
			if(Integer.valueOf(maxCode) <= Integer.valueOf(tmpUnit.getCode().substring(tmpUnit.getCode().length() - 3))){
				maxCode = String.valueOf(Integer.valueOf(tmpUnit.getCode().substring(tmpUnit.getCode().length() - 3)) + 1);
			}
		}
		maxCode = "000".substring(maxCode.length()) + maxCode;
		if(unit.getParentid() != null && !"".equalsIgnoreCase(String.valueOf(unit.getParentid()))){
			//��ѯ�ϼ�����
			sql = "from Unit where id = '" + String.valueOf(unit.getParentid())+"'";
			List parentUnit = this.unitDao.select(sql);
			if(parentUnit.size() <= 0){
				logger.info("��ѯ��λ" + unit.getParentid() + "ʧ�ܣ�û���ϼ���λ");
				throw new AppException("��ѯ�ϼ���λʧ�ܣ�");
			}
			tmpUnit = (Unit)this.unitDao.select(sql).get(0);
			maxCode = tmpUnit.getCode() + maxCode;
		}
		unit.setCode(maxCode);
		this.unitDao.save(unit);

		//log
		writeLog("���ӵ�λ", "������λ[" + unit + "]");
	}

	/**
	 * �޸ĵ�λ <br>
	 * 
	 */
	public void update(Unit unit) {
		this.unitDao.update(unit);

		//log
		writeLog("�޸ĵ�λ", "�޸ĵ�λ[" + unit + "]");
	}

	/**
	 * ɾ����λ
	 */
	public void delete(String unitid) {
		// �жϸõ�λ�����Ƿ����û�
		List users = userService.queryByUnitId(unitid);
		if (users != null && !users.isEmpty()) {
			logger.info("ɾ����λ" + unitid + "ʧ�ܣ��õ�λ��������û�");
			throw new AppException("�õ�λ��������û�");
		}

		this.unitDao.removeById(unitid);

		//log
		writeLog("ɾ����λ", "ɾ����λ[id=" + unitid + "]");
	}

	/**
	 * ���ݵ�λID��ѯ��λ����ϸ��Ϣ
	 */
	public Unit queryById(String unitid) {
		return this.unitDao.get(unitid);
	}

	/**
	 * ���ݵ�λ�����ѯ��λ����ϸ��Ϣ
	 */
	public Unit queryByCode(String code) {
		return this.unitDao.findUniqueBy("code", code);
	}

	/**
	 * ���������еĵ�λ��Ϣ�����������ϵĵ�λ
	 */
	public List<Unit> queryAll() {
		return this.unitDao.getAll();
	}

	/**get,set*/
	public void setUnitDao(UnitDao unitDao) {
		this.unitDao = unitDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
