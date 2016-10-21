package com.itsv.gbp.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 说明：该过滤器类功能是对请求进行设置编码。这是servlet 2.4新增加的功能。 <br>
 * 可通过初始化参数 RequestEncode 指定设定的编码名称。如果不设置，默认为GBK
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a> 
 * @since 2004-11-19
 */
public class ReqEncodeFilter implements Filter {

	private static String DEFAULT_ENCODE_NAME = "GBK";

	//编码名
	private String encodeName = null;

	/**
	 * 初始化方法。获得请求编码名称。如果不配置，默认编码是GBK
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		String param = filterConfig.getInitParameter("RequestEncode");
		if (param != null) {
			this.encodeName = param;
		} else {
			this.encodeName = DEFAULT_ENCODE_NAME;
		}

	}

	/**
	 * 处理请求，给请求设置编码属性。
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		req.setCharacterEncoding(this.encodeName);
		chain.doFilter(req, res);
	}

	public void destroy() {
	}

}
