package com.itsv.gbp.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.itsv.gbp.core.AppException;
import com.itsv.gbp.core.web.springmvc.BaseCURDController;

public class UiController extends BaseCURDController {

	private String leftPageView;
	private String mainPageView;
	private String topView;
	private String bottomView;
	protected static final Log logger = LogFactory.getLog(UiController.class);


	public ModelAndView showLeftPage(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		ModelAndView mnv1 = new ModelAndView(this.getLeftPageView());
		String menuCode = request.getParameter("menuCode");
		mnv1.addObject("menuCode", menuCode);
		return mnv1;
	}

	public ModelAndView showMainPage(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(this.getMainPageView());
		return mnv;
	}

	public ModelAndView showTopPage(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(this.getTopView());
		return mnv;
	}

	public ModelAndView showBottomPage(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		ModelAndView mnv = new ModelAndView(this.getBottomView());
		return mnv;
	}

	public String getLeftPageView() {
		return leftPageView;
	}

	public void setLeftPageView(String leftPageView) {
		this.leftPageView = leftPageView;
	}

	public String getMainPageView() {
		return mainPageView;
	}

	public void setMainPageView(String mainPageView) {
		this.mainPageView = mainPageView;
	}


	public String getTopView() {
		return topView;
	}

	public void setTopView(String topView) {
		this.topView = topView;
	}

	public String getBottomView() {
		return bottomView;
	}

	public void setBottomView(String bottomView) {
		this.bottomView = bottomView;
	}

}
