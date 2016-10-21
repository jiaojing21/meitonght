package com.itsv.platform.common.dictionary.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.itsv.platform.common.dictionary.bo.Itsv_dictionaryService;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;


/**
 * yangwenyan
 * 2007-08-17
 * �ֵ��ǩ
 * @preserve
 */
public class DictionaryNameTag extends TagSupport {

	private static final long serialVersionUID = 3628217258733508857L;
	private static final String DICTIONARY_CONFIG_ID = "dictionary.itsv_dictionaryService";

	private String typeName;
	private String code;
	private String hardCode;
	/**
	 * @preserve
	 */
	public int doStartTag() throws JspException {
		String str = "";
		try {
			//��ȡBeanFactory����
			WebApplicationContext wac = WebApplicationContextUtils
					.getWebApplicationContext(pageContext.getServletContext());

			//��ȡ�ֵ�ҵ�����
			Itsv_dictionaryService itsv_dictionaryService = (Itsv_dictionaryService) wac
					.getBean(DICTIONARY_CONFIG_ID);

			//������ѯ�ֵ����
			if (hardCode==null || hardCode.length()<=0){
				str = "";
			}
			if ((typeName==null || typeName.length()<=0)&&(code==null || code.length()<=0)){
				str = "";
			}else{
				if (code!=null && code.length()>0){
					str = itsv_dictionaryService.queryNameByHardCode(code, hardCode);
				}else{
					str = itsv_dictionaryService.queryNameByPNameHardCode(typeName, hardCode);
				}
			}
				
			//���
			pageContext.getOut().print(str);
		} catch (java.io.IOException ex) {
			throw new JspException(ex.getMessage());
		}
		/* ��ֹ�������岿�� */
		return SKIP_BODY;
	}

	/**
	 * @preserve
	 */
	public int doEndTag() throws JspException {
		/* ����ҳ���������� */
		return EVAL_PAGE;
	}
	/**
	 * @preserve
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @preserve
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @preserve
	 */
	public String getHardCode() {
		return hardCode;
	}
	/**
	 * @preserve
	 */
	public void setHardCode(String hardCode) {
		this.hardCode = hardCode;
	}

	/**
	 * @preserve
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}