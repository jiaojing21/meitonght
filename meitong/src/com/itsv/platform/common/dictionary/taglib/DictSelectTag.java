package com.itsv.platform.common.dictionary.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.itsv.platform.common.dictionary.bo.Itsv_dictionaryService;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;

public class DictSelectTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private static final String DICTIONARY_CONFIG_ID = "dictionary.itsv_dictionaryService";
	private String typeName;
	private String code;

	private String style;
	private String styleClass;
	private String value;
	private String onchange;
	private String name;
	private String id;

	private String defaultOptionValue;
	private String defaultOptionText;

	public int doStartTag() throws JspException {
		try {
			//读取BeanFactory对象
			WebApplicationContext wac = WebApplicationContextUtils
					.getWebApplicationContext(pageContext.getServletContext());

			//读取字典业务对象
			Itsv_dictionaryService itsv_dictionaryService = (Itsv_dictionaryService) wac
					.getBean(DICTIONARY_CONFIG_ID);
			String str = "";
			str = "<select name='"+name+"' ";
			if(this.id!=null&&!id.trim().equals("")){
				str=str+" id='"+id+"' ";
			}else{
				str=str+" id='"+name+"' ";
			}	
			//处理style
			if(this.style!=null&&!style.trim().equals("")){
				str=str+" style ='"+style+"' ";
			}	
			//处理styleClass
			if(this.styleClass!=null&&!styleClass.trim().equals("")){
				str=str+" class ='"+styleClass+"' ";
			}	
			//处理onchange
			if(this.onchange!=null&&!onchange.trim().equals("")){
				str=str+" onchange ='"+onchange+"' ";
			}				
			//闭包
			str=str+">";			
			
			//默认请选择
			if (defaultOptionValue==null || defaultOptionValue.length()<=0){
				defaultOptionValue = "";
			}if (defaultOptionText==null || defaultOptionText.length()<=0){
				defaultOptionText = "请选择";
			}
			if(value!=null&&!value.trim().equals("")){
				str=str+"<option value='"+defaultOptionValue+"'>"+defaultOptionText+"</option>";
			}else{
				str=str+"<option value='"+defaultOptionValue+"' selected>"+defaultOptionText+"</option>";
			}
			
			//创建查询字典对象
			String parentCode = "";
			if ((typeName==null || typeName.length()<=0)&&(code==null || code.length()<=0)){
				str+="";
			}else{
				List list = null;
				if (code!=null && code.length()>0){
					list = itsv_dictionaryService.queryNextListByCode(code);
				}else{
					list = itsv_dictionaryService.queryNextListByName(typeName);
				}
				if (list!=null && list.size()>0){
					for (int i = 0; i < list.size(); i++) {
						Itsv_dictionary dictionaryObj = (Itsv_dictionary) list
								.get(i);
						
						str+="<option value='"+dictionaryObj.getHardcode()+"'";
						if(value!=null&&value.equals(dictionaryObj.getHardcode())){
							str=str+" selected ";
						}
						
						str=str+">"+dictionaryObj.getDictname()+"</option>";
						//顺序：业务编码、字典名称、字典序号、层级编码、上级编码、级次
/*						str += "['" + dictionaryObj.getHardcode() + "','"
								+ dictionaryObj.getDictname() + "','"
								+ dictionaryObj.getDictno() + "','"
								+ dictionaryObj.getCode() + "','"
								+ dictionaryObj.getParentcode() + "','"
								+ String.valueOf(dictionaryObj.getCodeclass())
								+ "']";*/

					}
				}
			} 
			
			str=str+"</select>";
			//输出
			pageContext.getOut().print(str);
		} catch (Exception ex) {
			ex.printStackTrace();
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
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getDefaultOptionValue() {
		return defaultOptionValue;
	}

	public void setDefaultOptionValue(String defaultOptionValue) {
		this.defaultOptionValue = defaultOptionValue;
	}

	public String getDefaultOptionText() {
		return defaultOptionText;
	}

	public void setDefaultOptionText(String defaultOptionText) {
		this.defaultOptionText = defaultOptionText;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

}
