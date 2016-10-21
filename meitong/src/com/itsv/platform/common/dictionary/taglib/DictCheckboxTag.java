package com.itsv.platform.common.dictionary.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.itsv.platform.common.dictionary.bo.Itsv_dictionaryService;
import com.itsv.platform.common.dictionary.vo.Itsv_dictionary;

public class DictCheckboxTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String typeName;
	private String code;

	private String style;
	private String styleClass;
	private String value;
	private String name;
	private String id;
	private static final String DICTIONARY_CONFIG_ID = "dictionary.itsv_dictionaryService";
	
	public int doStartTag() throws JspException {
		try {
			//读取BeanFactory对象
			WebApplicationContext wac = WebApplicationContextUtils
					.getWebApplicationContext(pageContext.getServletContext());

			//读取字典业务对象
			Itsv_dictionaryService itsv_dictionaryService = (Itsv_dictionaryService) wac
					.getBean(DICTIONARY_CONFIG_ID);

			String str = "";
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
						str=str+"<input type='checkbox'  value='"+dictionaryObj.getHardcode()+"'";
						if(value!=null){
							String [] s = value.split(",");
							for(int j=0;j<s.length;j++){
								String v=(String)s[j].trim();
								if(v!=null&&v.equals(dictionaryObj.getHardcode())){
									str=str+" checked ";
									break;
								}
							}
						}
						str = str+"  name='"+name+"' ";
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
						str=str+">"+dictionaryObj.getDictname()+"&nbsp;&nbsp;|";
					}
				}
			} 
			//输出
			pageContext.getOut().print(str);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new JspException(ex.getMessage());
		}
		/* 禁止处理主体部分 */
		return SKIP_BODY;
	}	
	
	
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
