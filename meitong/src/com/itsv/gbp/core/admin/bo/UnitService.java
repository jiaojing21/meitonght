package com.itsv.gbp.core.admin.bo;

import java.util.List;

import org.apache.log4j.Logger;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.admin.dao.UnitDao;
import com.itsv.gbp.core.admin.vo.Unit;

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
public class UnitService extends LoggedService {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UnitService.class);

	private UnitDao unitDao;

	private UserService userService;

	/**
	 * 增加单位
	 */
	public void add(Unit unit) {
		Unit tmpUnit;
		//查询最大可用层级编码
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
			//查询上级编码
			sql = "from Unit where id = '" + String.valueOf(unit.getParentid())+"'";
			List parentUnit = this.unitDao.select(sql);
			if(parentUnit.size() <= 0){
				logger.info("查询单位" + unit.getParentid() + "失败：没有上级单位");
				throw new AppException("查询上级单位失败！");
			}
			tmpUnit = (Unit)this.unitDao.select(sql).get(0);
			maxCode = tmpUnit.getCode() + maxCode;
		}
		unit.setCode(maxCode);
		this.unitDao.save(unit);

		//log
		writeLog("增加单位", "新增单位[" + unit + "]");
	}

	/**
	 * 修改单位 <br>
	 * 
	 */
	public void update(Unit unit) {
		this.unitDao.update(unit);

		//log
		writeLog("修改单位", "修改单位[" + unit + "]");
	}

	/**
	 * 删除单位
	 */
	public void delete(String unitid) {
		// 判断该单位底下是否有用户
		List users = userService.queryByUnitId(unitid);
		if (users != null && !users.isEmpty()) {
			logger.info("删除单位" + unitid + "失败：该单位下面存在用户");
			throw new AppException("该单位下面存在用户");
		}

		this.unitDao.removeById(unitid);

		//log
		writeLog("删除单位", "删除单位[id=" + unitid + "]");
	}

	/**
	 * 根据单位ID查询单位的详细信息
	 */
	public Unit queryById(String unitid) {
		return this.unitDao.get(unitid);
	}

	/**
	 * 根据单位编码查询单位的详细信息
	 */
	public Unit queryByCode(String code) {
		return this.unitDao.findUniqueBy("code", code);
	}

	/**
	 * 检索出所有的单位信息。包括已作废的单位
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
