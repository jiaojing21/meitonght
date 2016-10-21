package com.itsv.annotation.fileUpload.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： file_upload 值对象类
 * 
 * @author swk
 * @since 2016-03-24
 * @version 1.0
 */

 @Entity
 @Table(name="file_upload")
public class FileUpload {
                    @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="FILE_NAME",length=500  )
	private String fileName; //original_name


    @Column(name="FILE_TIME",length=0  )
	private Date fileTime; //file_time

    @Column(name="DOWNLOADPATH",length=500  )
	private String downloadPath; //downloadpath

    @Column(name="FID",length=50  )
	private String fId; //fid

    @Column(name="OPERATOR",length=100  )
	private String operator; //operator

    @Column(name="TYPE",length=10  )
	private String type; //type
    
    
    

    /** 以下为get,set方法 */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getFileTime() {
    return this.fileTime;
  }

  public void setFileTime(Date fileTime) {
    this.fileTime = fileTime;
  }

	public String getDownloadPath() {
    return this.downloadPath;
  }

  public void setDownloadPath(String downloadPath) {
    this.downloadPath = downloadPath;
  }

	public String getFId() {
    return this.fId;
  }

  public void setFId(String fId) {
    this.fId = fId;
  }

	public String getOperator() {
    return this.operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

	public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
