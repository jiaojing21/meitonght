package com.itsv.gbp.core.cache.taglib;

import java.util.Collections;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.itsv.gbp.core.cache.util.MirrorCacheTool;

/**
 * ���ݱ�ǩ���������ã���ѯ�������ݣ������������ָ��������<br>
 * ��ǩʾ�����£�
 * <pre>
 * 	&lt; cache:query var="userList" region="user" where="status=1" orderby="userid asc" /&gt;
 * </pre>
 * ����˵�����£�<br>
 * <ul>
 * 	<li>var	- ����������ڸ�����ָ���ı��������С�������</li>
 *  <li>region	- Ҫ�����Ļ����������ơ������</li>
 *  <li>where	- ������������ѡ���Ϊ�ձ�ʾ�������л������</li>
 *  <li>orderby	- ������������ѡ�</li>
 * </ul>
 * 
 * �õ�����б�󣬿�ʹ��<c:forEach>���������
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-15 ����05:35:48
 * @version 1.0
 */
public class MirrorCacheQueryTag extends TagSupport {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MirrorCacheQueryTag.class);

	private static final long serialVersionUID = 1824606600648497170L;

	private String region; //����������

	private String var; //�����ŵı�����

	private String where; // ��������

	private String orderby;// ����ʽ

	public int doStartTag() throws JspException {
		List result = null;
		try {
			result = MirrorCacheTool.simplyQuery(getRegion(), this.where, this.orderby);
		} catch (Exception e) {
			//��ǩ����ʱ�������ִ�У������������Ϣ
			logger.error("ִ�л����ѯ��ǩʱ����.region=" + getRegion() + ",where=" + this.where + ",orderby="
					+ this.orderby, e);
		}
		if (result == null) {
			result = Collections.EMPTY_LIST;
		}
		this.pageContext.setAttribute(getVar(), result);

		return EVAL_PAGE;
	}

	/** ����Ϊ��ȡ���� */

	public Object getOrderby() {
		return orderby;
	}

	//	orderby����֧�ֱ��ʽ
	public void setOrderby(Object orderby) throws JspException {
		this.orderby = (String) ExpressionEvaluatorManager.evaluate("orderby", orderby.toString(),
				Object.class, this, pageContext);
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public Object getWhere() {
		return where;
	}

	//where����֧�ֱ��ʽ
	public void setWhere(Object where) throws JspException {
		this.where = (String) ExpressionEvaluatorManager.evaluate("where", where.toString(), Object.class,
				this, pageContext);
	}
}
