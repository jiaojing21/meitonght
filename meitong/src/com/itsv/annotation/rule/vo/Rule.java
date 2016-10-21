package com.itsv.annotation.rule.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 说明： 代金券规则 值对象类
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
	private String ruleName; // 规则名称

	@Column(name = "RULEDETAIL", length = 255)
	private String ruleDetail; // 规则详细

	@Column(name = "RULECRUX", length = 255)
	private String ruleCrux; // 规则关键字

	@Column(name = "VOUCHERID", length = 32)
	private String voucherId; // 代金券主键

	@Column(name = "AMOUNT", length = 10)
	private String amount; // 数量
	
	@Column(name = "TYPE", length = 2)
	private String type; //规则类型（0:新注册用户;1:分享;2:特定时间登陆用户;3:连续登陆用户）

	/** 以下为get,set方法 */
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
