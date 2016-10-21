package com.itsv.platform.common.dictionary.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.itsv.platform.common.dictionary.dao.Itsv_dictionaryDao;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.cache.util.MirrorCacheTool;
import com.itsv.gbp.core.orm.paged.IPagedList;

/*
 * 说明：处理对数据字典的业务操作
 *
 * @author milu
 * @since 2007-07-22
 * @version 1.0
 */
public class Itsv_dictionaryService extends BaseService {

  //数据访问层对象
  private Itsv_dictionaryDao itsv_dictionaryDao;
  private String region = "dictionary";

	/**
	 * 增加数据字典
	 */
	public void add(Itsv_dictionary itsv_dictionary) {
		Itsv_dictionary qryitsv_dictionary = new Itsv_dictionary();
		qryitsv_dictionary.setParentcode(itsv_dictionary.getParentcode());
		List<Itsv_dictionary> list = this.itsv_dictionaryDao.queryByObject(qryitsv_dictionary);
		if(list != null && list.size() != 0){
			int maxCode = 1;
			Long maxNo = Long.valueOf("1");
			for(Itsv_dictionary obj: list){
				if(obj.getDictno() > maxNo){
					maxNo = obj.getDictno();
				}
				String tmp = obj.getCode();
				tmp = tmp.substring(tmp.length() - 3);
				if(Integer.valueOf(tmp) > maxCode){
					maxCode = Integer.valueOf(tmp);
				}
			}
			itsv_dictionary.setDictno(maxNo + 1);
			String code = String.valueOf(maxCode + 1);
			String parentCode = "";
			if(itsv_dictionary.getCodeclass() > 1){
				parentCode = itsv_dictionary.getParentcode();
			}
			code = parentCode + "000".substring(code.length()) + code;
			itsv_dictionary.setCode(code);
		}else{
			itsv_dictionary.setDictno(Long.valueOf("1"));
			itsv_dictionary.setCode(itsv_dictionary.getParentcode() + "001");
		}
		this.itsv_dictionaryDao.save(itsv_dictionary);
	}

	/**
	 * 修改数据字典
	 */
	public void update(Itsv_dictionary itsv_dictionary) {
		this.itsv_dictionaryDao.update(itsv_dictionary);
	}

	/**
	 * 删除数据字典
	 */
	public void delete(Serializable id) {
		this.itsv_dictionaryDao.removeById(id);
	}

	/**
	 * 根据ID查询数据字典的详细信息
	 */
	public Itsv_dictionary queryById(Serializable itsv_dictionaryid) {
		return this.itsv_dictionaryDao.get(itsv_dictionaryid);
	}

	/**
	 * 根据Code查询数据字典的详细信息
	 */
	public Itsv_dictionary queryByCode(String code) {
		Itsv_dictionary itsv_dictionary = new Itsv_dictionary();
		itsv_dictionary.setCode(code);
		List<Itsv_dictionary> list = this.queryByVO(itsv_dictionary);
		if(list.size()>0){
			itsv_dictionary = list.get(0);
		}
		return itsv_dictionary;
	}

	/**
	 * 获取所有的数据字典对象
	 */
	public List<Itsv_dictionary> queryAll() {
		return this.itsv_dictionaryDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<Itsv_dictionary> queryByVO(Itsv_dictionary itsv_dictionary) {
		return this.itsv_dictionaryDao.queryByObject(itsv_dictionary);
	}
	
	/**
	 * 根据TypeName查询出所有下一级子列表
	 */
	public List<Itsv_dictionary> queryNextListByName(String typeName) {
		List<Itsv_dictionary> returnList = new ArrayList<Itsv_dictionary>();
		List<Itsv_dictionary> parentlist = MirrorCacheTool.simplyQuery(region,"dictname='"+typeName+"'","dictno");
		if(parentlist!=null && parentlist.size()>=1){
			returnList = MirrorCacheTool.simplyQuery(region,"parentcode = '"+((Itsv_dictionary) parentlist.get(0)).getCode()+"'","dictno");
		}
		return returnList;
	}
	
	/**
	 * 根据层级编码Code查询出所有下一级子列表
	 */
	public List<Itsv_dictionary> queryNextListByCode(String code) {
		List<Itsv_dictionary> returnList = new ArrayList<Itsv_dictionary>();
		returnList = MirrorCacheTool.simplyQuery(region,"parentcode = '"+code+"'","dictno"); 
		return returnList;
	}
	
	/**
	 * 根据层级编码Code和业务编码HARDCODE查询出字典的中文名字
	 */
	public String queryNameByHardCode(String code,String hardCode) {
		String returnName = "";
		List<Itsv_dictionary> list = MirrorCacheTool.simplyQuery(region,"parentcode = '"+code+"' and hardcode='"+hardCode+"'"); 
		if (list!=null && list.size()>0){
			returnName = list.get(0).getDictname();
		}
		return returnName;
	}
	
	/**
	 * 根据上级字典名字和业务编码HARDCODE查询出字典的中文名字
	 */
	public String queryNameByPNameHardCode(String name,String hardCode) {
		String returnName = "";
		List<Itsv_dictionary> currentList = new ArrayList<Itsv_dictionary>();
		List<Itsv_dictionary> parentlist = MirrorCacheTool.simplyQuery(region,"dictname = '"+name+"'");
		if(parentlist!=null && parentlist.size()>=1){
			currentList = MirrorCacheTool.simplyQuery(region,"parentcode = '"+((Itsv_dictionary) parentlist.get(0)).getCode()+"' and hardcode='"+hardCode+"'");
			if (currentList!=null && currentList.size()>0){
				returnName = currentList.get(0).getDictname();
			}
		}
		
		return returnName;
	}
	/**
	 * 根据上级字典名字和业务编码CODE查询出字典的中文名字
	 */
	public String queryNameByPNameCode(String name,String code) {
		String returnName = "";
		List<Itsv_dictionary> currentList = new ArrayList<Itsv_dictionary>();
		List<Itsv_dictionary> parentlist = MirrorCacheTool.simplyQuery(region,"dictname = '"+name+"'");
		if(parentlist!=null && parentlist.size()>=1){
			currentList = MirrorCacheTool.simplyQuery(region,"parentcode = '"+((Itsv_dictionary) parentlist.get(0)).getCode()+"' and code='"+code+"'");
			if (currentList!=null && currentList.size()>0){
				returnName = currentList.get(0).getDictname();
			}
		}
		
		return returnName;
	}
	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, Itsv_dictionary itsv_dictionary) {
		return this.itsv_dictionaryDao.queryByObject(records, itsv_dictionary);
	}

	public void setItsv_dictionaryDao(Itsv_dictionaryDao itsv_dictionaryDao) {
		this.itsv_dictionaryDao = itsv_dictionaryDao;
	}

}