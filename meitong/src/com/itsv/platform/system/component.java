package com.itsv.platform.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.web.springmvc.BaseController;

public class component extends BaseController {
	private String treeview;
	private String dateview;
	private String selectlistview;
	private String selectoneview;

	public ModelAndView tree(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getTreeview());
		return mnv;
	}
	public ModelAndView date(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getDateview());
		return mnv;
	}
	public ModelAndView selectlist(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getSelectlistview());
		return mnv;
	}
	public ModelAndView selectone(HttpServletRequest request, HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(getSelectoneview());
		return mnv;
	}
	
	public String getDateview() {
		return dateview;
	}
	public void setDateview(String dateview) {
		this.dateview = dateview;
	}
	public String getSelectlistview() {
		return selectlistview;
	}
	public void setSelectlistview(String selectlistview) {
		this.selectlistview = selectlistview;
	}
	public String getSelectoneview() {
		return selectoneview;
	}
	public void setSelectoneview(String selectoneview) {
		this.selectoneview = selectoneview;
	}
	public String getTreeview() {
		return treeview;
	}
	public void setTreeview(String treeview) {
		this.treeview = treeview;
	}
	
}
