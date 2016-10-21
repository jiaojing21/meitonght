package com.itsv.annotation.rule.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ˵���� ����ȯ���� ֵ������
 * 
 * @author yfh
 * @since 2016-04-20
 * @version 1.0
 */

@Entity
@Table(name = "rule")
public class Rule {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID")
	private String id;

	@Column(name = "RULENAME", length = 255)
	private String ruleName; // ��������

	@Column(name = "RULEDETAIL", length = 255)
	private String ruleDetail; // ������ϸ

	@Column(name = "RULECRUX", length = 255)
	private String ruleCrux; // ����ؼ���

	@Column(name = "VOUCHERID", length = 32)
	private String voucherId; // ����ȯ����

	@Column(name = "AMOUNT", length = 10)
	private String amount; // ����
	
	@Column(name = "TYPE", length = 2)
	private String type; //�������ͣ�0:��ע���û�;1:����;2:�ض�ʱ���½�û�;3:������½�û���

	/** ����Ϊget,set���� */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleDetail() {
		return this.ruleDetail;
	}

	public void setRuleDetail(String ruleDetail) {
		this.ruleDetail = ruleDetail;
	}

	public String getRuleCrux() {
		return this.ruleCrux;
	}

	public void setRuleCrux(String ruleCrux) {
		this.ruleCrux = ruleCrux;
	}

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
