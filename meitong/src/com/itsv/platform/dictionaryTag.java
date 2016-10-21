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
 * 字典标签
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
			//读取BeanFactory对象
			WebApplicationContext wac = WebApplicationContextUtils
					.getWebApplicationContext(pageContext.getServletContext());

			//读取字典业务对象
			Itsv_dictionaryService itsv_dictionaryService = (Itsv_dictionaryService) wac
					.getBean(DICTIONARY_CONFIG_ID);

			//创建查询字典对象
			Itsv_dictionary itsv_dictionary = new Itsv_dictionary();
			itsv_dictionary.setDictname(typeName);
			List parentlist = itsv_dictionaryService.queryByVO(itsv_dictionary);
			String str = "";
			if (parentlist.size() > 0) {
				//创建查询字典对象
				Itsv_dictionary detailitsv_dictionary = new Itsv_dictionary();
				detailitsv_dictionary
						.setParentcode(((Itsv_dictionary) parentlist.get(0))
								.getCode());
				List list = itsv_dictionaryService
						.queryByVO(detailitsv_dictionary);
				//生成字典数据
				if (list.size() > 0) {
					str = "[";
					for (int i = 0; i < list.size(); i++) {
						Itsv_dictionary dictionaryObj = (Itsv_dictionary) list
								.get(i);
						//顺序：业务编码、字典名称、字典序号、层级编码、上级编码、级次
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
			//输出
			pageContext.getOut().print(str);
		} catch (java.io.IOException ex) {
			throw new JspException(ex.getMessage());
		}
		/* 禁止处理主体部分 */
		return SKIP_BODY;
	}

	/**
	 * @preserve
	 */
	public int doEndTag() throws JspException {
		/* 处理页面其它部分 */
		return EVAL_PAGE;
	}

	/**
	 * @preserve
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}