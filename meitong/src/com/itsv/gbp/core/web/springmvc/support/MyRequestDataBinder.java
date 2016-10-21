package com.itsv.gbp.core.web.springmvc.support;

import javax.servlet.ServletRequest;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.itsv.gbp.core.web.springmvc.BaseController;

/**
 * 自定义数据绑定类。主要为了给传来的参数增加通用的前缀。<br>
 * 
 * @see ServletRequestDataBinder
 * @see BaseController
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-9 下午10:57:57
 * @version 1.0
 */
public class MyRequestDataBinder extends WebDataBinder {

	private String prefix; //参数前缀

	private String prefixSeparator; //参数前缀与参数之间的分隔符

	public MyRequestDataBinder(Object target) {
		super(target);
	}

	public MyRequestDataBinder(Object target, String objectName) {
		super(target, objectName);
	}

	public void bind(ServletRequest request) {
		MutablePropertyValues mpvs = new ServletRequestParameterPropertyValues(request, getPrefix(),
				getPrefixSeparator());
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			bindMultipartFiles(multipartRequest.getFileMap(), mpvs);
		}
		doBind(mpvs);
	}

	public void closeNoCatch() throws ServletRequestBindingException {
		if (getBindingResult().hasErrors()) {
			throw new ServletRequestBindingException("Errors binding onto object '"
					+ getBindingResult().getObjectName() + "'", new BindException(getBindingResult()));
		}
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefixSeparator() {
		return prefixSeparator;
	}

	public void setPrefixSeparator(String prefixSeparator) {
		this.prefixSeparator = prefixSeparator;
	}

}
