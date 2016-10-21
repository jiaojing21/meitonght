package com.itsv.platform;

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
public class dictionaryTag extends TagSupport {

	private static final long serialVersionUID = 3628217258733508857L;
	private static final String DICTIONARY_CONFIG_ID = "dictionary.itsv_dictionaryService";

	private String typeName;

	/**
	 * @preserve
	 */
	public int doStartTag() throws JspException {
		try {
			//��ȡBeanFactory����
			WebApplicationContext wac = WebApplicationContextUtils
					.getWebApplicationContext(pageContext.getServletContext());

			//��ȡ�ֵ�ҵ�����
			Itsv_dictionaryService itsv_dictionaryService = (Itsv_dictionaryService) wac
					.getBean(DICTIONARY_CONFIG_ID);

			//������ѯ�ֵ����
			Itsv_dictionary itsv_dictionary = new Itsv_dictionary();
			itsv_dictionary.setDictname(typeName);
			List parentlist = itsv_dictionaryService.queryByVO(itsv_dictionary);
			String str = "";
			if (parentlist.size() > 0) {
				//������ѯ�ֵ����
				Itsv_dictionary detailitsv_dictionary = new Itsv_dictionary();
				detailitsv_dictionary
						.setParentcode(((Itsv_dictionary) parentlist.get(0))
								.getCode());
				List list = itsv_dictionaryService
						.queryByVO(detailitsv_dictionary);
				//�����ֵ�����
				if (list.size() > 0) {
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
			} else {
				str = "[]";
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
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}