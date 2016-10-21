package com.itsv.annotation.suggestion.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.itsv.annotation.util.CustomDateSerializer;

/**
 * ˵���� suggestion ֵ������
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */

 @Entity
 @Table(name="suggestion")
public class Suggestion {
                  @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="SPOKESMAN",length=50  )
	private String spokesMan; //����û�

    @Column(name="CREATETIME",length=0  )
	private Date createtime; //���ʱ��

    @Column(name="CONTENT",length=500  )
	private String content; //����

    @Column(name="FLAG",length=500  )
   	private String flag; //�ظ���ʾ
    
    	@Column(name="ANSWER",length=500  )
	private String answer; //�ظ�

    @Column(name="REMARK_1",length=50  )
	private String remark1; //remark_1

    @Column(name="REMARK_2",length=50  )
	private String remark2; //remark_2

    /** ����Ϊget,set���� */
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpokesMan() {
    return this.spokesMan;
  }

  public void setSpokesMan(String spokesMan) {
    this.spokesMan = spokesMan;
  }
  @JsonSerialize(using=CustomDateSerializer.class) 
	public Date getCreatetime() {
    return this.createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

	public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

	public String getAnswer() {
    return this.answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

	public String getRemark1() {
    return this.remark1;
  }

  public void setRemark1(String remark1) {
    this.remark1 = remark1;
  }

	public String getRemark2() {
    return this.remark2;
  }

  public void setRemark2(String remark2) {
    this.remark2 = remark2;
  }
  
  public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}


}
