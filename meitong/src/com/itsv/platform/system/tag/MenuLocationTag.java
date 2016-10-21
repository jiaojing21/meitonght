package com.itsv.platform.system.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.itsv.gbp.core.admin.bo.MenuService;

public class MenuLocationTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String menucode;

	String joint;

	String head;

	public void setHead(String head) {
		this.head = head;
	}

	public void setJoint(String joint) {
		this.joint = joint;
	}



	public int doStartTag() throws JspException {

		MenuService ms = (MenuService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(
						pageContext.getServletContext()).getBean(
						"admin.menuService");
		/*
		if (ms == null) {
			System.out.println("null");
		} else {
			System.out.println("not null");
		}
		*/

		//链接串非必填项
		if(this.joint==null){
			this.setJoint(">>");
		}
		
		if(menucode!=null&&menucode.equals("")){
			return EVAL_PAGE;
		}
		try {
			JspWriter out = pageContext.getOut();
			List<String> nlist = new ArrayList<String>();
			StringBuffer sb = new StringBuffer("");
			
			nlist = ms.queryMenu(menucode);
			String s = "";
			for (int i = 0; i < nlist.size(); i++) {

				if (i + 1 >= nlist.size() ) {
					
					//首字符串非必填项，当未填写时默认为"当前位置"
					if(this.head==null){
						head="当前位置:";
					}
					
					s = (String) nlist.get(i) + s;
					s = this.head +  s;
				} else {
					s = this.joint + (String) nlist.get(i) + s;
				}
			}
			sb.append("" + s + "");
			out.print(sb.toString());

		} catch (Exception e) {
			throw new JspException(e);
		}
		return EVAL_PAGE;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}

}
