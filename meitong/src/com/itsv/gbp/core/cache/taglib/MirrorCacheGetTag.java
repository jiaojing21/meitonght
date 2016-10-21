package com.itsv.gbp.core.cache.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.itsv.gbp.core.cache.util.MirrorCacheTool;

/**
 * �����ȡ��ǩ������ָ����ֵ�����Ҷ�Ӧ�Ļ�����󣬲����������<br>
 * ��ǩʾ�����£�
 * <pre>
 * 	&lt; cache:get var="u" region="user" key="100" targetprop="userName" out="1" /&gt;
 * </pre>
 * ����˵�����£�<br>
 * <ul>
 * 	<li>var	- 	���������ֵ������������ڸ�����ָ���ı��������С���ѡ��</li>
 *  <li>region	- Ҫ�����Ļ����������ơ������</li>
 *  <li>key	- 	�����key��������</li>
 *  <li>targetprop	- ����ָ��Ҫ����������ĸ����ԡ���ѡ�������Ա�ʾ�����������������ĳ���������ԡ�</li>
 * 	<li>out		- �Ƿ񽫻�õĻ������������ҳ�档��ѡ� <b>Ĭ�����</b></li>
 * </ul>
 * 
 * �õ�����б�󣬿�ʹ��<c:forEach>���������

 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-15 ����06:04:46
 * @version 1.0
 */
public class MirrorCacheGetTag extends TagSupport {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MirrorCacheGetTag.class);

	private static final long serialVersionUID = -6674275988680577191L;

	private String region; //����������

	private String var; //�����ŵı�����

	private boolean out = true; //�Ƿ�ֱ�������ҳ��

	private Object key; // ��������

	private String targetprop;// ��Ҫ��õ�Ŀ������

	public int doStartTag() throws JspException {
		Object value = null;
		try {
			//���ָ������������ȡ��������ֵ������ȡ����������
			if (getTargetprop() != null) {
				value = MirrorCacheTool.getProp(getRegion(), getKey().toString(), getTargetprop());
			} else {
				value = MirrorCacheTool.get(getRegion(), getKey().toString());
			}

		} catch (Exception e) {
			//��ǩ����ʱ�������ִ�У������������Ϣ
			logger.error("ִ�л����ȡ��ǩʱ����.region=" + getRegion() + ",key=" + getKey() + ",targetProp="
					+ getTargetprop(), e);
		}

		//���Ҫ����������������
		if (value != null && out) {
			try {
				pageContext.getOut().print(value);
			} catch (IOException e) {
				logger.error("���������������ֵʱ����", e);
			}

		}

		//���ָ����varֵ���򽫽������ñ�����
		if (value != null && getVar() != null) {
			this.pageContext.setAttribute(getVar(), value);
		}

		return EVAL_PAGE;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) throws JspException {
		this.key = ExpressionEvaluatorManager.evaluate("key", key.toString(), Object.class, this,
				pageContext);
	}

	public String getOut() {
		return String.valueOf(out);
	}

	public void setOut(String out) {
		if ("0".equalsIgnoreCase(out) || "f".equalsIgnoreCase(out) || "false".equalsIgnoreCase(out)) {
			this.out = false;
		}
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTargetprop() {
		return targetprop;
	}

	public void setTargetprop(String targetprop) {
		this.targetprop = targetprop;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	/** ����Ϊget,set����	 */

}
