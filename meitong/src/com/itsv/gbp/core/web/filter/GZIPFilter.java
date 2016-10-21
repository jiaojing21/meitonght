package com.itsv.gbp.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ������ѹ��������������ҳ����ѹ����Ȼ������ͻ��ˡ����Խ�ʡ���紫������������ӿ��û������ٶȡ� <br>
 * ��ϸ���˼���ǣ� <br>
 * ��һ����chain.doFilter(req, wrappedResponse)֮ǰû�н���ѹ���� <br>
 * �ڶ�����chain.doFilter(req, wrappedResponse)֮���ѹ�����ݣ����ͷ���Դ
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a> 
 * @since 2004-11-22
 */
public class GZIPFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		if (req instanceof HttpServletRequest) {
			//ת��Ϊhttp ���������Ӧ
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			String ae = request.getHeader("Accept-Encoding");
			if (ae != null && ae.toLowerCase().indexOf("gzip") != -1) {
				GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);

				chain.doFilter(req, wrappedResponse);

				//������ѹ�����ݾ����������
				wrappedResponse.finishResponse();

				return;
			}
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig filterConfig) {
	}

	public void destroy() {
	}
}