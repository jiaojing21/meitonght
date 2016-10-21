package com.itsv.annotation.fileUpload.bo;

import java.io.Serializable;
import java.util.List;

import com.itsv.annotation.fileUpload.dao.FileUploadDao;
import com.itsv.annotation.fileUpload.vo.FileUpload;

import com.itsv.gbp.core.admin.BaseService;
import com.itsv.gbp.core.orm.paged.IPagedList;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional; 
/*
 * 说明：处理对file_upload的业务操作
 *
 * @author swk
 * @since 2016-03-24
 * @version 1.0
 */
 @Service @Transactional 
public class FileUploadService extends BaseService {

  //数据访问层对象
  @Autowired
  private FileUploadDao fileUploadDao;

	/**
	 * 增加file_upload
	 */
	public void add(FileUpload fileUpload) {
		this.fileUploadDao.save(fileUpload);
	}

	/**
	 * 修改file_upload
	 */
	public void update(FileUpload fileUpload) {
		this.fileUploadDao.update(fileUpload);
	}

	/**
	 * 删除file_upload
	 */
	public void delete(Serializable id) {
		this.fileUploadDao.removeById(id);
	}

	/**
	 * 根据ID查询file_upload的详细信息
	 */
	public FileUpload queryById(Serializable fileUploadid) {
		return this.fileUploadDao.get(fileUploadid);
	}

	/**
	 * 获取所有的file_upload对象
	 */
	public List<FileUpload> queryAll() {
		return this.fileUploadDao.getAll();
	}

	/**
	 * 组合条件查询
	 */
	public List<FileUpload> queryByVO(FileUpload fileUpload) {
		return this.fileUploadDao.queryByObject(fileUpload);
	}

	/**
	 * 组合条件的分页查询
	 */
	public IPagedList queryByVO(IPagedList records, FileUpload fileUpload) {
		return this.fileUploadDao.queryByObject(records, fileUpload);
	}

}