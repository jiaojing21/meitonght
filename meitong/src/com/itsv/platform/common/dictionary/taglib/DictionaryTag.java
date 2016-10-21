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
public class DictionaryTag extends TagSupport {

	private static final long serialVersionUID = 3628217258733508857L;
	private static final String DICTIONARY_CONFIG_ID = "dictionary.itsv_dictionaryService";

	private String typeName;
	private String code;

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
			String parentCode = "";
			if ((typeName==null || typeName.length()<=0)&&(code==null || code.length()<=0)){
				str="[]";
			}else{
				List list = null;
				if (code!=null && code.length()>0){
					list = itsv_dictionaryService.queryNextListByCode(code);
				}else{
					list = itsv_dictionaryService.queryNextListByName(typeName);
				}
				if (list!=null && list.size()>0){
					str = "[";
					for (int i = 0; i < list.size(); i++) {
						Itsv_dictionary dictionaryObj = (Itsv_dictionary) list
								.get(i);
						//˳��ҵ����롢�ֵ����ơ��ֵ���š��㼶���롢�ϼ����롢����
						str += "['" + dictionaryObj.getHardcode() + "','"
								+ dictionaryObj.getDictname() + "','"
								+ dictionaryObj.getDictno() + "','"
								+ dictionaryObj.getCode() + "','"
								+ dictionaryObj.getParentcode() + "','"
								+ String.valueOf(dictionaryObj.getCodeclass())
								+ "']";
						if (i != (list.size() - 1))
							str = str + ",";
					}
					str += "]";
				} else {
					str = "[]";
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

}