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
 * ˵���������file_upload��ҵ�����
 *
 * @author swk
 * @since 2016-03-24
 * @version 1.0
 */
 @Service @Transactional 
public class FileUploadService extends BaseService {

  //���ݷ��ʲ����
  @Autowired
  private FileUploadDao fileUploadDao;

	/**
	 * ����file_upload
	 */
	public void add(FileUpload fileUpload) {
		this.fileUploadDao.save(fileUpload);
	}

	/**
	 * �޸�file_upload
	 */
	public void update(FileUpload fileUpload) {
		this.fileUploadDao.update(fileUpload);
	}

	/**
	 * ɾ��file_upload
	 */
	public void delete(Serializable id) {
		this.fileUploadDao.removeById(id);
	}

	/**
	 * ����ID��ѯfile_upload����ϸ��Ϣ
	 */
	public FileUpload queryById(Serializable fileUploadid) {
		return this.fileUploadDao.get(fileUploadid);
	}

	/**
	 * ��ȡ���е�file_upload����
	 */
	public List<FileUpload> queryAll() {
		return this.fileUploadDao.getAll();
	}

	/**
	 * ���������ѯ
	 */
	public List<FileUpload> queryByVO(FileUpload fileUpload) {
		return this.fileUploadDao.queryByObject(fileUpload);
	}

	/**
	 * ��������ķ�ҳ��ѯ
	 */
	public IPagedList queryByVO(IPagedList records, FileUpload fileUpload) {
		return this.fileUploadDao.queryByObject(records, fileUpload);
	}

}