package com.itsv.platform.common.dictionary.taglib;

import java.util.ArrayList;
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
public class DictionaryNextListTag extends TagSupport {

	private static final long serialVersionUID = 3628217258733508857L;
	private static final String DICTIONARY_CONFIG_ID = "dictionary.itsv_dictionaryService";

	private String var; //�����ŵı�����
	
	private String typeName;
	private String code;

	/**
	 * @preserve
	 */
	public int doStartTag() throws JspException {
		List<Itsv_dictionary> result = null;
		try {
			//��ȡBeanFactory����
			WebApplicationContext wac = WebApplicationContextUtils
					.getWebApplicationContext(pageContext.getServletContext());

			//��ȡ�ֵ�ҵ�����
			Itsv_dictionaryService itsv_dictionaryService = (Itsv_dictionaryService) wac
					.getBean(DICTIONARY_CONFIG_ID);

			//������ѯ�ֵ����
			String parentCode = "";
			if ((typeName==null || typeName.length()<=0)&&(code==null || code.length()<=0)){
				result = new ArrayList<Itsv_dictionary>();
			}else{
				if (code!=null && code.length()>0){
					result = itsv_dictionaryService.queryNextListByCode(code);
				}else{
					result = itsv_dictionaryService.queryNextListByName(typeName);
				}
				if (result==null){
					result = new ArrayList<Itsv_dictionary>();
				}
			}
			
			this.pageContext.setAttribute(getVar(), result);

		} catch (Exception e) {
			throw new JspException(e.getMessage());
		}
		/* ��ֹ�������岿�� */
		return SKIP_BODY;
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
	public int doEndTag() throws JspException {
		/* ����ҳ���������� */
		return EVAL_PAGE;
	}

	/**
	 * @preserve
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}

}