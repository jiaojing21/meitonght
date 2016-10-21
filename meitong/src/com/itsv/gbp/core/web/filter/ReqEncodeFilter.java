package com.itsv.gbp.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * ˵�����ù������๦���Ƕ�����������ñ��롣����servlet 2.4�����ӵĹ��ܡ� <br>
 * ��ͨ����ʼ������ RequestEncode ָ���趨�ı������ơ���������ã�Ĭ��ΪGBK
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a> 
 * @since 2004-11-19
 */
public class ReqEncodeFilter implements Filter {

	private static String DEFAULT_ENCODE_NAME = "GBK";

	//������
	private String encodeName = null;

	/**
	 * ��ʼ���������������������ơ���������ã�Ĭ�ϱ�����GBK
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
	 * �������󣬸��������ñ������ԡ�
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		req.setCharacterEncoding(this.encodeName);
		chain.doFilter(req, res);
	}

	public void destroy() {
	}

}
