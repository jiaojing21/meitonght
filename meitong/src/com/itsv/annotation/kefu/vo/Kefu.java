package com.itsv.annotation.kefu.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ˵���� kefu ֵ������
 * 
 * @author swk
 * @since 2016-03-25
 * @version 1.0
 */

 @Entity
 @Table(name="kefu")
public class Kefu {
                  @Id
@GenericGenerator(name="idGenerator", strategy = "uuid")
@GeneratedValue(generator="idGenerator")
@Column(name="ID")
	private String id;

    @Column(name="EMPLOYEENUMBER",length=50  )
	private String employeeNumber; //Ա�����

    @Column(name="NAME",length=50  )
	private String name; //Ա������

    @Column(name="WORKTIME",length=255  )
	private String workTime; //����ʱ��

    @Column(name="ANSWER",length=500  )
	private String answer; //�Զ���

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

	public String getEmployeeNumber() {
    return this.employeeNumber;
  }

  public void setEmployeeNumber(String employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

	public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

	public String getWorkTime() {
    return this.workTime;
  }

  public void setWorkTime(String workTime) {
    this.workTime = workTime;
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

}
