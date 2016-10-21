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
 * �Զ������ݰ��ࡣ��ҪΪ�˸������Ĳ�������ͨ�õ�ǰ׺��<br>
 * 
 * @see ServletRequestDataBinder
 * @see BaseController
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-9-9 ����10:57:57
 * @version 1.0
 */
public class MyRequestDataBinder extends WebDataBinder {

	private String prefix; //����ǰ׺

	private String prefixSeparator; //����ǰ׺�����֮��ķָ���

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
